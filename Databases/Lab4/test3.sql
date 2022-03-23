use FlowerShop
GO

exec addToTables 'Occasion'
exec addToTables 'Bouquet'
exec addToTables 'Bouquet_Kind'
exec addToTables 'Flower'
exec addToTables 'Bouquet_Flower'
exec addToTables 'Supplier'
exec addToTables 'Stock'
exec addToTables 'Orders'
GO

---get bouquets which were ordered and payed
CREATE OR ALTER VIEW getBouquetsPayed as
	SELECT B.Name, O.Order_status
	FROM Bouquet B inner join Orders O ON O.Bouquet_ID = B.Bouquet_ID
	WHERE O.Order_status = 'payed'

GO

---get flowers in stock which are in bouquets with occasion
CREATE OR ALTER VIEW getBouquetsFlowerOccasion as
	SELECT F.Name, COUNT(*) AS Number
	FROM Bouquet_Kind B 
		inner join Occasion O on O.Occasion_ID = B.Bouquet_ID
		inner join Bouquet BB on BB.Bouquet_ID = B.Bouquet_ID
		inner join Bouquet_Flower BF on BF.Bouquet_ID = BB.Bouquet_ID
		inner join Flower F on F.Flower_ID = BF.Flower_ID
		inner join Stock S on S.Flower_ID = F.Flower_ID
		inner join Supplier Su on Su.Supplier_ID = F.Supplier_ID
	GROUP BY F.Name
GO
	
exec addToViews 'getBouquetsFlowerOccasion'
exec addToViews 'getBouquetsWithOccasion'
exec addToTests 'test3'
exec connectTableTotest 'Occasion', 'test3', 800, 1
exec connectTableTotest 'Stock', 'test3', 400, 7
exec connectTableTotest 'Bouquet_FLower', 'test3', 500, 6
exec connectTableTotest 'Bouquet_Kind', 'test3', 500, 5
exec connectTableTotest 'Flower', 'test3', 1000, 4
exec connectTableTotest 'Supplier', 'test3',700, 3
exec connectTableTotest 'Bouquet', 'test3', 1000, 2
exec connectTableTotest 'Orders', 'test3', 600, 8
exec connectViewsTotest 'getBouquetsFlowerOccasion', 'test3'
exec connectViewsTotest 'getBouquetsWithOccasion', 'test3'
GO


CREATE OR ALTER PROCEDURE populateTableFlower(@rows int)as
	while @rows > 0 begin
		insert into Flower values(@rows, (select top 1 Supplier_ID from Supplier order by NEWID()), 'Name'+CAST(floor(250*rand())as varchar(10)), 'Color', 'Kind', floor(200*rand()))
		set @rows = @rows-1
	end
GO
CREATE OR ALTER PROCEDURE populateTableStock(@rows int)as
	declare @f_id int
	while @rows > 0 begin
		set @f_id = (select top 1 Flower_ID from Flower order by NEWID())
		while @f_id in (select Flower_ID from Stock)
			set @f_id = (select top 1 Flower_ID from Flower order by NEWID())
		insert into Stock values(@rows, @f_id, floor(100*rand()))
		set @rows = @rows-1
	end
GO

CREATE OR ALTER PROCEDURE populateTableOccasion(@rows int) as
	WHILE @rows > 0 begin
		insert into Occasion(Occasion_ID, Description, Date) VALUES (@rows, 'Occasion description', DATEADD(DAY, ABS(CHECKSUM(NEWID()) % 3650),'2000-01-01'))
		set @rows = @rows-1
	END
GO

CREATE OR ALTER PROCEDURE populateTableBouquet(@rows int)as
	while @rows > 0 begin
		insert into Bouquet values(@rows, 'name', floor(100*rand()), floor(200*rand()), 'color')
		set @rows = @rows-1
	end
GO

CREATE OR ALTER PROCEDURE populateTableBouquet_Kind(@rows int)as
	DECLARE @bid int
	DECLARE @oid int
	WHILE @rows > 0 BEGIN
		SET @bid = (SELECT TOP 1 Bouquet_ID FROM Bouquet ORDER BY NEWID())
		SET @oid = (SELECT TOP 1 Occasion_ID FROM Occasion ORDER BY NEWID())
		WHILE exists(SELECT * FROM Bouquet_Kind WHERE Bouquet_ID = @bid and Occasion_ID = @oid)BEGIN
			SET @bid = (SELECT TOP 1 Bouquet_ID FROM Bouquet ORDER BY NEWID())
			SET @oid = (SELECT top 1 Occasion_ID FROM Occasion ORDER BY NEWID())
		END
		INSERT INTO Bouquet_Kind VALUES(@bid, @oid, 'season', 'size')
		SET @rows = @rows-1
	END

GO
create or alter procedure populateTableBouquet_Flower(@rows int)as
	declare @b_id int
	declare @f_id int
	while @rows > 0 begin
		set @b_id = (select top 1 Bouquet_ID from Bouquet order by NEWID())
		set @f_id = (select top 1 Flower_ID from Flower order by NEWID())
		while exists(select * from Bouquet_Flower where Bouquet_ID = @b_id and Flower_ID = @f_id)begin
			set @b_id = (select top 1 Bouquet_ID from Bouquet order by NEWID())
			set @f_id = (select top 1 Flower_ID from Flower order by NEWID())
		end
		
		insert into Bouquet_Flower values(@f_id, @b_id, floor(50*rand()), 'Flower_Name'+CAST(floor(250*rand())as varchar(10)))
		set @rows = @rows-1
	end

GO

exec runTest 'test3'

select* from Flower
select * from Bouquet_Flower
select * from Bouquet_Kind
select * from Occasion