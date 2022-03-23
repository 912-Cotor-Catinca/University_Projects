create table Ta(
	aid int primary key,
	a2 int unique,
	x int
)

create table Tb(
	bid int primary key,
	b2 int,
	x int
)

create table Tc(
	cid int primary key,
	aid int references Ta(aid),
	bid int references Tb(bid)
)

select * from Ta
select * from Tb
select * from Tc

GO
create or alter procedure insertIntoTa(@rows int) as
	declare @max int
	set @max = @rows*2 +100
	while @rows > 0 begin
		insert into Ta values(@rows, @max, @rows%120)
		set @rows = @rows - 1
		set @max = @max - 2
	end

GO
create or alter procedure insertIntoTb(@rows int) as
	while @rows > 0 begin
		insert into Tb values(@rows, @rows % 840, @rows % 140)
		set @rows = @rows -1
	end

GO
create or alter procedure insertIntoTc(@rows int) as
	declare @aid int
	declare @bid int
	while @rows > 0 begin
		set @aid = (select top 1 aid from Ta order by NEWID())
		set @bid = (select top 1 bid from Tb order by NEWID())
		insert into Tc values (@rows, @aid, @bid)
		set @rows = @rows - 1
	end

exec insertIntoTa 10000
exec insertIntoTb 12000
exec insertIntoTc 4000



select * from Ta order by aid ---clustered index scan
select * from Ta where aid = 1 ---clustered index seek
select a2 from Ta  ---nonclustered index scan
select a2 from Ta where a2 = 1 ---nonclustered index seek
select x from Ta where a2 = 19000 ---key lookup

select * from Tb where b2 = 40 -- clustered index scan 0.05 cost

create nonclustered index index2 on Tb(b2) include( x)
drop index index2 on Tb

select * from Tb where b2 = 40 ---nonclustered index seek 0.003 cost

create nonclustered index index1 on Ta(x)
drop index index1 on Ta

GO
create or alter view view1 as
	select top 1000 T1.x, T2.b2
	from Tc T3 join Ta T1 on T3.aid = T1.aid 
			   join Tb T2 on T3.bid = T2.bid
	where T2.b2 > 500 and T1.x < 20
GO
select * from view1
---cost with indexes - 0.18
---cost without indexes - 0.27

