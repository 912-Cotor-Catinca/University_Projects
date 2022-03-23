use FlowerShop
GO

DROP PROCEDURE IF EXISTS addToTables
DROP PROCEDURE IF EXISTS addToViews
DROP PROCEDURE IF EXISTS addToTests
DROP PROCEDURE IF EXISTS connectTableTotest
DROP PROCEDURE IF EXISTS connectViewsTotest

DROP TABLE IF EXISTS TestTables
GO

select * from TestTables
select *from Tables
select * from Views
select * from Tests
select * from TestViews
select * from TestRuns
select * from TestRunTables
select * from TestRunViews
GO

---procedure to populate Tables 
create or alter procedure addToTables(@tablename varchar(50))as
	if @tablename in (select Name from Tables) begin
		print 'Table already present in Tables'
		return
	end
	if @tablename not in (select TABLE_NAME from INFORMATION_SCHEMA.TABLES) begin
		print 'Table not present in the database'
		return
	end
	insert into Tables(Name) values(@tablename)
GO

---procedure to populate Views 
create or alter procedure addToViews(@viewName varchar(50)) as
	if @viewName in (select Name from Views) begin
		print 'View already present in views'
		return
	end
	if @viewName not in (select TABLE_NAME from INFORMATION_SCHEMA.VIEWS) begin
		print 'View not present in the database'
		return
	end
	insert into Views(Name) values(@viewName)
GO

---procedure to populate Tests
create or alter procedure addToTests(@testName varchar(50)) as
	if @testName in (select Name from Tests) begin
		print 'Test already present in Tests'
		return
	end
	insert into Tests(Name) values (@testName)
GO

---procedure to populate TestTables
create or alter procedure connectTableTotest(@tableName varchar(50), @testName varchar(50), @rows int, @pos int) as
	if @tableName not in( select Name from Tables) begin
		print 'Table not present in Tables'
		return
	end
	if @testName not in(select Name from Tests) begin
		print 'Tests not present in Tests'
		return
	end
	if exists(
		select *
		from TestTables T1 join Tests T2 on T1.TestID = T2.TestID
		where T2.Name = @testName and Position = @pos
		) begin
		print 'Position provided conflicts with previous positions'
		return
	end
	insert into TestTables(TestID, TableID, NoOfRows, Position)values(
		(select Tests.TestID from Tests where Name = @testName),
		(select Tables.TableID from Tables where Name = @tableName),
		@rows,
		@pos
	)

GO

---procedure to populate TestViews 
create or alter procedure connectViewsTotest(@viewName varchar(50), @testName varchar(50))as
	if @viewName not in(select Name from Views)begin
		print 'View not present in Views'
		return
	end
	if @testName not in (select Name from Tests) begin
		print 'Tests not in Tests'
		return
	end
	insert into TestViews (TestID, ViewID) values(
		(select Tests.TestID from Tests where Name = @testName),
		(select Views.ViewID from Views where Name = @viewName)
	)
GO

DROP PROCEDURE IF EXISTS runTest
GO
create or alter procedure runTest(@testName varchar(50)) as
	if @testName not in (select Name from Tests) begin
		print 'test not in Tests'
		return
	end
	declare @command varchar(50)
	declare @testStartTime datetime2
	declare @startTime datetime2
	declare @endTime datetime2
	declare @table varchar(50)
	declare @rows int
	declare @pos int
	declare @view varchar(50)
	declare @testId int
	select @testId=TestID from Tests where Name=@testName
	declare @testRunId int
	set @testRunId = (select max(TestRunId)+1 from TestRuns)
	if @testRunId is null
		set @testRunId = 0
	declare tableCursor cursor scroll for
		select T1.Name, T2.NoOfRows, T2.Position
		from Tables T1 join TestTables T2 on T1.TableID = T2.TableID
		where T2.TestID = @testId
		order by T2.Position
	declare viewCursor cursor for
		select V.Name
		from Views V join TestViews TV on V.ViewID = TV.ViewID
		where TV.TestID = @testId

	set @testStartTime = SYSDATETIME()
	open tableCursor
	fetch last from tableCursor into @table, @rows, @pos
	while @@FETCH_STATUS = 0 begin
		exec('delete from ' + @table)
		fetch prior from tableCursor into @table, @rows,@pos
	end
	close tableCursor

	open tableCursor
	SET IDENTITY_INSERT TestRuns ON
	insert into TestRuns(TestRunID, Description, StartAt) values(@testRunId, 'Tests result for: '+@testName, @testStartTime)
	SET IDENTITY_INSERT TestRuns OFF
	fetch tableCursor into @table, @rows, @pos
	while @@FETCH_STATUS = 0 begin
		print @rows
		print @table
		set @command = 'populateTable'+@table
		if @command not in (select ROUTINE_NAME from INFORMATION_SCHEMA.ROUTINES) begin
			print @command +'does not exists'
			return
		end
		set @startTime = SYSDATETIME()
		exec @command @rows
		set @endTime = SYSDATETIME()
		insert into TestRunTables (TestRunID, TableID, StartAt, EndAt)values(@testRunId, (select TableID from Tables where Name=@table), @startTime, @endTime)
		fetch tableCursor into @table, @rows, @pos
	end
	close tableCursor
	deallocate tableCursor

	open viewCursor
	fetch viewCursor into @view
	while @@FETCH_STATUS = 0 begin
		set @command = 'select * from ' + @view
		set @startTime = SYSDATETIME()
		exec (@command)
		set @endTime = SYSDATETIME()
		insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values(@testRunId, (select ViewID from Views where Name=@view), @startTime, @endTime)
		fetch viewCursor into @view
	end
	close viewCursor
	deallocate viewCursor

	update TestRuns
	set EndAt=SYSDATETIME()
	where TestRunID = @testRunId

GO