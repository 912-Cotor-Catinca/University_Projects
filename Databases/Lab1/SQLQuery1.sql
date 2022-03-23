USE FlowerShop;

DROP TABLE IF EXISTS Bouquet_Kind;
DROP TABLE IF EXISTS Bouquet_Flower;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Bouquet;
DROP TABLE IF EXISTS Occasion;
DROP TABLE IF EXISTS Stock;
DROP TABLE IF EXISTS Flower
DROP TABLE IF EXISTS Supplier;
DROP TABLE IF EXISTS Payment_Method;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Versions;
DROP TABLE IF EXISTS Catalogue_Bouquets

CREATE TABLE Versions(
	id_version INT
);


CREATE TABLE Customer(
	Customer_ID int primary key,
	Fname varchar(25) not null,
	Lname varchar(25) not null,
	Telephone varchar(10) not null,
	Email varchar(50) not null,
	City varchar(20) not null,
	Country varchar(30) not null
);

CREATE TABLE Payment_Method(
	Payment_ID int primary key,
	Detail varchar(50) not null
);


CREATE TABLE Supplier(
	Supplier_ID int primary key,
	Name varchar(20) not null,
	Phone varchar(10) not null,
	City varchar(20)
);

CREATE TABLE Flower(
	Flower_ID int primary key,
	Supplier_ID int foreign key references Supplier(Supplier_ID),
	Name varchar(20) not null,
	Color varchar(15) not null,
	Kind varchar(20) not null,
	Price int not null
);

CREATE TABLE Stock(
	Stock_ID int primary key,
	Flower_ID int not null unique,
	foreign key (Flower_ID) references Flower(Flower_ID) on delete cascade,
	Available_quantity int not null
);

CREATE TABLE Occasion(
	Occasion_ID int primary key,
	Description varchar(50) not null,
	Date date
);

CREATE TABLE Bouquet(
	Bouquet_ID int primary key,
	Name varchar(50) not null,
	Quantity_of_flowers int not null,
	Price int not null,
	Color varchar(50)
);


CREATE TABLE Orders(
	Order_ID int primary key,
	Customer_ID int not null,
	Payment_ID int not null unique,
	Bouquet_ID int not null,
	Date_of_order date not null,
	Date_of_delivery date not null,
	Place_of_delivery varchar(50) not null,
	Order_status varchar(20) not null,
	foreign key (Customer_ID) references Customer(Customer_ID),
	foreign key (Payment_ID) references Payment_Method(Payment_ID),
	foreign key (Bouquet_ID) references Bouquet(Bouquet_ID)
);


CREATE TABLE Bouquet_Flower(
	Flower_ID int not null,
	Bouquet_ID int not null,
	Quantity_of_flower int not null,
	Flower_name varchar(20),
	foreign key (Flower_ID) references Flower(FLower_ID),
	foreign key (Bouquet_ID) references Bouquet(Bouquet_ID),
	constraint PK_BouquetFlower primary key(Flower_ID, Bouquet_ID)
);

CREATE TABLE Bouquet_Kind(
	Bouquet_ID int not null,
	Occasion_ID int not null,
	Season varchar(10) not null,
	Size varchar(10) not null,
	foreign key (Bouquet_ID) references Bouquet(Bouquet_ID),
	foreign key (Occasion_ID) references Occasion(Occasion_ID),
	constraint PK_Bouquet_Kind primary key(Bouquet_ID, Occasion_ID)
);

---inserts-----
Insert Into Supplier(Supplier_ID, Name, Phone, City) VALUES(1, 'local farm', '0745478963', 'Cluj');
Insert Into Supplier(Supplier_ID, Name, Phone, City) VALUES(2, 'wholesalers', '0745478963', 'Bucharest');
Insert Into Supplier(Supplier_ID, Name, Phone, City) VALUES(3, 'flower auctions', '0745478963', 'Bucharest');
Insert Into Supplier(Supplier_ID, Name, Phone, City) VALUES(4, 'Cesarom', '072356987', 'Budapest');
Insert Into Supplier(Supplier_ID, Name, Phone, City) VALUES(5, 'Blooms by the box', '0778945612', 'Bucharest');

INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(1, 1, 'Rose', 'Red', 'Climbing rose', 12);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(2, 1, 'Daisy', 'White', 'garden', 6);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(3, 2, 'Tulip', 'White', 'home', 10);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(4, 2, 'Tulip', 'purple', 'CLimbing rose', 12);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(5, 3, 'Rose', 'Pinkyy', 'Miniature Roses', 15);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(6, 4, 'Iris', 'Purple', 'Garden', 8);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(7, 5, 'Lily', 'Pink', 'Oriental', 25);
INSERT INTO Flower (Flower_Id, Supplier_ID, Name, Color, Kind, Price) VALUES(8, 4, 'Anemone', 'White', 'Spirng flowering', 7);

INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City,Country) VALUES(1, 'John', 'Walker', '074687588', 'john@gmail.com', 'Cluj', 'Romania');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email,City,Country) VALUES(2, 'Jimmy', 'Page', '072387588', 'jimmy@gmail.com', 'Bucharest', 'Romania');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City,Country) VALUES(3, 'Alex', 'Smith', '0723456978', 'alex@gmail.com', 'Bucharest', 'Romania');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City,Country) VALUES(4, 'Mary', 'Jones', '0733554477', 'mary@gmail.com', 'Frankfurt', 'Germany');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City,Country) VALUES(5, 'Lary', 'Johnson', '0712345698', 'lary@gmail.com', 'Prague', 'Czech Republic');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City, Country) VALUES(6, 'Ann', 'Parker', '0778945632', 'ann@gmail.com', 'London', 'UK');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City, Country) VALUES(7, 'Jake', 'Miller', '0741526396', 'jake@gmail.com', 'Bucharest', 'Romania');
INSERT INTO Customer(Customer_ID, Fname, Lname, Telephone, Email, City, Country) VALUES(8, 'Tony', 'Soprano', '0742516388', 'tony@gmail.com', 'Cluj', 'Romania');


INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(1, 'Spring Madness', 7, 50, 'White, Pink, Yellow');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(2,'Summer Vibes', 7, 90, 'Yellow, Purple');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(3, 'Spring Blossom', 3, 40, 'Pink, Red, Purple, White');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(4, 'Fall', 9, 120, 'Orange, Blue, Green');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(5, 'Bloom', 11, 150, 'Red, Pink');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(6, 'Winter is here', 5, 55, 'Blue, White');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(7, 'Wedding', 9, 100, 'White');
INSERT INTO Bouquet(Bouquet_ID, Name, Quantity_of_flowers, Price, Color) VALUES(8, 'Love', 15, 155, 'Red');

INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(111, 'by card');
INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(201, 'cash, 50%paid');
INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(354, 'cash');
INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(417, 'by card');
INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(002, 'by card');
INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(200, 'by card');
INSERT INTO Payment_Method(Payment_ID, Detail) VALUES(202, 'cash');

INSERT INTO Stock(Stock_ID,Flower_ID, Available_quantity) VALUES(101, 1, 20);
INSERT INTO Stock(Stock_ID,Flower_ID, Available_quantity) VALUES(102, 2, 10);
INSERT INTO Stock(Stock_ID,Flower_ID, Available_quantity) VALUES(103, 3, 9);
INSERT INTO Stock(Stock_ID,Flower_ID, Available_quantity) VALUES(104, 4, 25);
INSERT INTO Stock(Stock_ID,Flower_ID, Available_quantity) VALUES(105, 5, 30);
INSERT INTO Stock(Stock_ID,Flower_ID, Available_quantity) VALUES(106, 6, 15);

INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(1, 'mother day', '2021/03/08');
INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(2, 'parents aniverssary', '2021/04/27');
INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(3, 'girlfriend birthday', '2021/08/08');
INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(4, 'wife birthday', '2021/02/15');
INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(5, 'sister birthday', '2021/10/10');
INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(6, 'first day of school', '2021/09/15');
INSERT INTO Occasion(Occasion_ID, Description, Date)VALUES(7, 'valentine day', '2021/02/14');

INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(224, 1, 111, 1, '2021/03/07', '2021/03/08', 'Cluj', 'payed');
INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(45, 2, 417, 1, '2021/04/27', '2021/04/27', 'Cluj', 'payed');
INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(724, 4, 002, 2, '2021/09/14', '2021/09/15', 'Cluj', 'payed');
INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(410, 4, 201, 8, '2021/02/14', '2021/02/14', 'Cluj', 'not payed');
INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(411, 5, 354, 7, '2021/02/13', '2021/02/14', 'Cluj', 'payed');
INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(413, 5, 202, 7, '2021/05/13', '2021/05/16', 'Satu Mare', ' half payed');
INSERT INTO Orders(Order_ID, Customer_ID,Payment_ID, Bouquet_ID, Date_of_order, Date_of_delivery, Place_of_delivery, Order_status)
	VALUES(412, 6, 200, 5, '2021/03/07', '2021/03/07', 'Cluj', 'payed');

INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(1, 3, 3, 'Tulip');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(1, 4, 3, 'Tulip');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(1, 2, 1, 'Daisy');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(8, 1, 5, 'Rose');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(7, 5, 3, 'Rose');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(3, 7, 5, 'Lily');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(2, 7, 3, 'Lily');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(4, 8, 3, 'Anemone');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(4, 6, 5, 'Iris');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(6, 3, 5, 'Tulip');
INSERT INTO Bouquet_Flower(Bouquet_ID, Flower_ID, Quantity_of_flower, Flower_name) VALUES(5, 1, 5, 'Rose');



INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(1, 1, 'spring', 'medium');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(3, 1, 'spring', 'big');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(8, 3, 'summer', 'big');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(4, 2, 'fall', 'medium');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(8, 4, 'spring', 'medium');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(2, 5, 'summer', 'small');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(4, 6, 'fall', 'small');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(1, 6, 'fall', 'medium');
INSERT INTO Bouquet_Kind(Bouquet_ID, Occasion_ID, Season, Size)VALUES(8, 7, 'spring', 'big');

---inserts done-----


---updates---
UPDATE Supplier
SET Name = 'Select FLower'
WHERE Supplier_ID = 2 

---discounts---
UPDATE Bouquet
SET Price = Price / 2
WHERE Price BETWEEN 30 AND 55

UPDATE Customer
SET Telephone = '0713553121'
WHERE Email LIKE 'john%'
---updates done----

---deletes----
--DELETE 
--FROM Customer
--WHERE Fname IN('Jimmy')


--DELETE 
--FROM FLower
--WHERE Kind LIKE 'Climbing%'

---deletes done----


---queries---
--a) union and or---
---The following SQL statement returns the prices of all flowers and bouquets with the price less than 25 and bigger than 70.
SELECT 'Flower' AS Type, Name ,Price from Flower
UNION
SELECT 'Bouquet', Name,Price from Bouquet
WHERE Bouquet.Quantity_of_flowers < 10 AND (Bouquet.Price < 25 OR Bouquet.Price > 70)


---Find the bouquets which contain at least 5 flowers and contain roses or tulips

SELECT * FROM Bouquet_Flower
SELECT * FROM Flower

SELECT  f.Flower_ID, f.Name, f.Price*b.Quantity_of_flower AS TotalSum
FROM Flower f
INNER JOIN Bouquet_Flower b ON b.Flower_ID = f.Flower_ID
WHERE b.Quantity_of_flower >= 5 AND (f.Name = 'Rose' OR f.Name = 'Tulip')



---union and or done--


---b) intersect and in---
---Display the stock for each flower and its available quantity
SELECT * FROM Flower
SELECT * FROM Stock
SELECT f.Name, f.Color, s.Available_quantity,  s.Available_quantity*f.Price as TotalSum 
	FROM Flower f
	LEFT JOIN Stock s
	ON s.Flower_ID = s.Flower_ID
INTERSECT
	SELECT f.Name, f.Color, s.Available_quantity,  s.Available_quantity*f.Price as TotalSum  
	FROM Flower f
	RIGHT JOIN Stock s
	ON f.Flower_ID = s.Flower_ID

---Find all the customers who bought a wedding bouquet and payed tha bouquet or it is half payed
SELECT * FROM Bouquet
SELECT * FROM Customer
SELECT * FROM Orders

SELECT DISTINCT Fname, Lname
	FROM Customer
	INNER JOIN Orders
	ON Customer.Customer_ID = Orders.Customer_ID
WHERE Orders.Bouquet_ID IN(SELECT Bouquet.Bouquet_ID FROM Bouquet INNER JOIN Orders
							ON Bouquet.Bouquet_ID = Orders.Bouquet_ID
							WHERE Bouquet.Name ='Wedding' AND (Orders.Order_status = 'payed' OR Orders.Order_status = 'half payed'));

---c) except and not in---
SELECT * FROM Bouquet
SELECT * FROM Flower
SELECT * FROM Bouquet_Flower

---display the bouquets which do not contain roses 
SELECT Bouquet.Bouquet_ID, Name
	FROM Bouquet
	INNER JOIN Bouquet_Flower
	ON Bouquet.Bouquet_ID = Bouquet_Flower.Bouquet_ID
EXCEPT
SELECT Bouquet.Bouquet_ID, Name
	FROM Bouquet
	INNER JOIN Bouquet_Flower
	ON Bouquet.Bouquet_ID = Bouquet_Flower.Bouquet_ID
	WHERE Bouquet_Flower.Flower_name ='Rose'


---Display the customers who paid more than 100 on a bouquet
SELECT * FROM Bouquet
SELECT * FROM Customer
SELECT * FROM Orders

SELECT Fname, LName FROM Customer
	INNER JOIN Orders ON Customer.Customer_ID = Orders.Customer_ID
WHERE Orders.Order_ID NOT IN(SELECT Order_ID FROM Orders INNER JOIN Bouquet b ON
		Orders.Bouquet_ID = b.Bouquet_ID
		WHERE b.Price < 100);

---d) INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN

---display all customers  that payed their bouquet
SELECT * FROM Bouquet
SELECT * FROM Customer
SELECT * FROM Orders
SELECT c.FName, c.Lname, o.Date_of_order, b.Name
FROM Customer c
INNER JOIN Orders o ON c.Customer_ID = o.Customer_ID
INNER JOIN Bouquet b ON b.Bouquet_ID = o.Bouquet_ID
WHERE o.Order_status ='payed'

---display all the orders that are payed by card
SELECT * FROM Payment_Method
SELECT * FROM Orders

SELECT o.Date_of_order, p.Detail, p.Payment_ID
FROM Orders o
LEFT JOIN Payment_Method p ON o.Payment_ID = p.Payment_ID
WHERE p.Detail = 'by card' AND o.Order_status='payed'

---display all the orders that are not fully payed
SELECT * FROM Payment_Method
SELECT * FROM Orders
SELECT o.Date_of_order, p.Detail, p.Payment_ID
FROM Payment_Method p 
RIGHT JOIN Orders o ON o.Payment_ID = p.Payment_ID
WHERE p.Detail LIKE '%paid%' AND o.Order_status='not payed'

---Display the bouquets with the flowers associated to them and the occasion 
SELECT * FROM Bouquet
SELECT * FROM Flower
SELECT * FROM Occasion
SELECT * FROM Bouquet_Kind
SELECT * FROM Bouquet_Flower

SELECT f.Name, f.Color, b.Name, o.Description
FROM Flower f
FULL JOIN Bouquet_Flower bf on bf.Flower_ID = f.Flower_ID
FULL JOIN Bouquet b on b.Bouquet_ID = bf.Bouquet_ID
FULL JOIN Bouquet_Kind bk ON bk.Bouquet_ID = b.Bouquet_ID
Right JOIN Occasion o ON o.Occasion_ID = bk.Occasion_ID

--e)IN oparator
---Find the customers who are in the same city as the suppliers
SELECT Fname, Lname FROM Customer
WHERE City IN (SELECT City FROM Supplier)

---Find the flowers that are in bouquets and their quantity required for a bouquet is less than the minimum available quantity in stock
SELECT Flower_ID, Name FROM Flower
WHERE Name IN(SELECT Name FROM Bouquet_Flower 
				WHERE Quantity_of_flower < (SELECT MIN(Available_quantity) FROM Stock)
)

--f) EXISTS

---Display the occasions and the dates in which there were orders
SELECT * FROM Occasion
SELECT * FROM Orders

SELECT Description, Date
FROM Occasion
WHERE EXISTS(SELECT * FROM Orders o WHERE o.Date_of_delivery = Occasion.Date OR o.Date_of_order = Occasion.Date)
	
--See the top 3 customers who have placed more than 1 order	
SELECT TOP 3 c.Fname, c.Lname
FROM Customer c
WHERE EXISTS(SELECT COUNT(Order_ID), Customer_ID FROM Orders WHERE Customer_ID = c.Customer_ID 
			GROUP BY Customer_ID
			HAVING COUNT(Order_ID) > 1)

---g) subqueries FROM
---Display the flower's kinds and the average price
SELECT Kind, AveragePrice
FROM ( SELECT Kind, AVG(Price) AS AveragePrice
	FROM Flower
	GROUP BY Kind
)	AS BouquetsSummary
ORDER BY AveragePrice

---Display all the orders from customers which are not from Romania
SELECT x.Order_ID, x.Fname, x.Lname 
FROM(
	SELECT c.Fname, c.Lname, o.Order_ID
	FROM Orders o
	INNER JOIN Customer c ON o.Customer_ID = c.Customer_ID
	WHERE c.Country <> 'Romania'
)AS x


---h)GROUP BY

--Display the top selling bouquet
SELECT * FROM Orders
SELECT * FROM Bouquet

SELECT TOP 1 o.Bouquet_ID, COUNT(o.Order_ID) AS NrOfOrders
FROM Orders o
GROUP BY o.Bouquet_ID

---Display the number of suppliers for a flower
SELECT *FROM Supplier
SELECT * FROM Flower

SELECT COUNT(Supplier_ID) as SupplierCOUNT, Name
FROM Flower
GROUP BY Name
ORDER BY COUNT(Supplier_ID) DESC

---Dispplay the number of flowers that are more than 2 group by color
SELECT COUNT(Flower_ID) as FlowerCOUNT, Color
FROM Flower
GROUP BY Color
HAVING COUNT(Flower_ID) >= 2

---Display the flower ids which have the average quantity bigger than the average quantity from stock
SELECT * FROM Stock
SELECT * FROM Flower

SELECT f.Flower_ID,  AVG(Available_quantity) as AverageQuantity
FROM Stock
INNER JOIN Flower f ON f.Flower_ID = Stock.Flower_ID
GROUP BY f.Flower_ID
HAVING AVG(Available_quantity) > (SELECT AVG(Available_quantity) FROM Stock)


---Display all the flowers with the biggest number of apperances in bouquets
SELECT * FROM Flower
SELECT * FROM Bouquet_Flower

SELECT f.Flower_ID
FROM Bouquet_Flower f
INNER JOIN Flower ON Flower.Flower_ID = f.Flower_ID
GROUP BY f.Flower_ID 
HAVING COUNT(*) = (SELECT MAX(flowers) as Most_Flower FROM (
						SELECT f.Flower_ID, COUNT(*) as flowers
						FROM Bouquet_Flower f, Flower
						WHERE f.Flower_ID = Flower.Flower_ID
						GROUP BY f.Flower_ID
					) t
)


--i)ANY and ALL
---Display the flowers that are in bouquets and their quantity is less than the available quantity from stocks
SELECT DISTINCT f.Color, b.Flower_name 
FROM Flower f
LEFT JOIN Bouquet_Flower b ON f.Flower_ID = b.Flower_ID
WHERE b.Quantity_of_flower < ANY(
	SELECT s.Available_quantity
	FROM Stock s
)

---aggregation--the same thing as above
SELECT DISTINCT f.Color, b.Flower_name 
FROM Flower f
LEFT JOIN Bouquet_Flower b ON f.Flower_ID = b.Flower_ID
WHERE b.Quantity_of_flower < (
	SELECT MAX(s.Available_quantity)
	FROM Stock s
)

---Display bouquets which cost as much as a flower
SELECT b.Price, b.Name
FROM Bouquet b
WHERE b.Price = ANY( SELECT Price FROM Flower)

---the same thing as above
SELECT b.Price, b.Name
FROM Bouquet b
WHERE b.Price IN(SELECT Price FROM Flower)


---Display the flowers which distributors are not local farm and make a discount of 10%
SELECT * FROM Supplier
SELECT f.Name, f.Color, f.Price -(f.Price /10) AS NewPrice, f.Supplier_ID
FROM Flower f
WHERE f.Supplier_ID <> ALL(
	SELECT s.Supplier_ID
	FROM Supplier s
	WHERE s.Name ='local farm'
)
ORDER BY f.Price

---the same as above^
SELECT f.Name, f.Color, f.Price - (f.Price /10) AS NewPrice, f.Supplier_ID
FROM Flower f
WHERE f.Supplier_ID not in(
	SELECT s.Supplier_ID
	FROM Supplier s
	WHERE s.Name ='local farm'
)
ORDER BY f.Price

		
---Display all the bouquets that cost more than maximum price from flowers
SELECT Price FROM Bouquet
SELECT Price FROM Flower		

SELECT b.Bouquet_ID
FROM Bouquet b
GROUP BY b.Bouquet_ID
HAVING MAX(b.Price) > ALL(SELECT Price FROM Flower)

SELECT b.Bouquet_ID
FROM Bouquet b
GROUP BY b.Bouquet_ID
HAVING MAX(b.Price) > (SELECT MAX(Price) FROM Flower)