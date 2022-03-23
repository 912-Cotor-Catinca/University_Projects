use FlowerShop
GO

exec addToTables 'Supplier'
GO

---get all the suppliers group by city
CREATE OR ALTER VIEW getSuppliersByCity AS
	SELECT S.City, COUNT(*) AS Number_Of_Suppliers
	FROM Supplier S
	GROUP BY S.City

GO
exec addToViews 'getSuppliersByCity'
exec addToTests 'test1'
exec connectTableTotest 'Supplier', 'test1' ,700, 1
exec connectViewsTotest 'getSuppliersByCity', 'test1'
GO

CREATE OR ALTER PROCEDURE populateTableSupplier(@rows int) as
	while @rows > 0 begin
		insert into Supplier values(@rows, 'Name', 'Phone', 'City'+CAST(floor(100*rand())as varchar(10)))
		set @rows = @rows-1
	end
GO
execute runTest 'test1'


delete from Bouquet_Flower
delete from Flower
delete from Supplier

select * from Flower

GO