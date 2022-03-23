use FlowerShop
GO

exec addToTables 'Payment_Method'
exec addToTables 'Orders'

DROP PROCEDURE IF EXISTS populateTableOrders

GO
---get the orders payed by cash
CREATE OR ALTER VIEW getOrdersPayedByCard as
	SELECT P.Payment_ID, P.Detail, O.Order_Status
	FROM Payment_Method P inner join Orders O on O.Payment_ID = P.Payment_ID
	WHERE P.Detail = 'card' and O.Order_status = 'payed'
GO

exec addToViews 'getOrdersPayedByCard'
exec addToTests 'test2'
exec connectTableTotest 'Payment_Method', 'test2', 800, 1
exec connectTableTotest 'Orders', 'test2', 400, 2
exec connectViewsTotest 'getOrdersPayedByCard', 'test2'
GO


CREATE OR ALTER PROCEDURE populateTablePayment_Method(@rows int)as
	WHILE @rows > 0 BEGIN
		declare @detail varchar(50)
		IF @rows % 2 = 0
			set @detail = 'card'
		ELSE
			set @detail = 'cash'
		insert into Payment_Method(Payment_ID, Detail) VALUES(@rows, @detail)
		set @rows = @rows-1
	END
GO

CREATE OR ALTER PROCEDURE populateTableOrders(@rows int)as
	DECLARE @payed varchar(20)
	WHILE @rows > 0 BEGIN
		DECLARE @p_id int
		SET @p_id = (SELECT TOP 1 Payment_ID FROM Payment_Method ORDER BY NEWID())
		WHILE @p_id in (SELECT Payment_ID FROM Orders) begin
			SET @p_id = (SELECT TOP 1 Payment_ID FROM Payment_Method ORDER BY NEWID())
		END
		IF @rows % 2 = 0
			SET @payed = 'not payed'
		ELSE
			SET @payed = 'payed'
		insert into Orders(Order_ID, Customer_ID, Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status) VALUES
		(@rows,(select top 1 Customer_ID from Customer order by Customer_ID),@p_id,(select top 1 Bouquet_ID from Bouquet order by Bouquet_ID),
		 SYSDATETIME(),SYSDATETIME(),'Place',@payed)
		set @rows = @rows-1
	END
GO

execute runTest 'test2'

select * from Payment_Method
select * from Orders

GO