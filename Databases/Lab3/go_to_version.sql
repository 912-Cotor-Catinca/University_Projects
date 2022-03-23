USE [FlowerShop]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

DROP PROCEDURE IF EXISTS setDateFromOccasionChar;
DROP PROCEDURE IF EXISTS setDateFromOccasionDate;
DROP PROCEDURE IF EXISTS addAgeToCustomer;
DROP PROCEDURE IF EXISTS removeAgeToCustomer;
DROP PROCEDURE IF EXISTS addDefaultToAgeFromCustomer;
DROP PROCEDURE IF EXISTS removeDefaultToAgeFromCustomer;
DROP PROCEDURE IF EXISTS addCatalogue_Bouquets;
DROP PROCEDURE IF EXISTS removeCatalogue_Bouquets;
DROP PROCEDURE IF EXISTS addPKCatalogue;
DROP PROCEDURE IF EXISTS removePKCatalogue;
DROP PROCEDURE IF EXISTS addCandidateFlower;
DROP PROCEDURE IF EXISTS removeCandidateFlower;
DROP PROCEDURE IF EXISTS addForeignKeyCB;
DROP PROCEDURE IF EXISTS removeForeignKeyCB;
DROP PROCEDURE IF EXISTS go_to_version;
GO

DROP TABLE IF EXISTS proceduresTable;
DROP TABLE IF EXISTS version_change;
DROP TABLE IF EXISTS Catalogue_Bouquets;
DROP TABLE IF EXISTS Versions;
ALTER TABLE Customer
DROP CONSTRAINT df_Age
ALTER TABLE Customer
DROP COLUMN Age;
ALTER TABLE Flower
DROP CONSTRAINT UC_Flower
GO

--a)
CREATE PROCEDURE [dbo].[setDateFromOccasionChar]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	---modify a column type
	PRINT 'modify column Date From Occasion from type date to varchar(50)'
	ALTER TABLE Occasion
	ALTER COLUMN Date VARCHAR(50)
	
END
GO

CREATE PROCEDURE [dbo].[setDateFromOccasionDate]
AS
BEGIN
	SET NOCOUNT ON;
	--- modify the type of a column
	ALTER TABLE Occasion
	ALTER COLUMN Date date
	PRINT('modify column Date From Occasion from type varchar(50) to date')
END
GO

---b)
CREATE PROCEDURE [dbo].[addAgeToCustomer]
AS
BEGIN
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--- add a new column
	ALTER TABLE Customer
	ADD Age int
	PRINT('add a new column in Customer table named Age')
END
GO

CREATE PROCEDURE [dbo].[removeAgeToCustomer]
AS
BEGIN
	SET NOCOUNT ON;
	-- remove a column
	ALTER TABLE Customer
	DROP COLUMN Age
	PRINT('remove a new column in Customer table named Age')
END
GO

---c)
CREATE PROCEDURE [dbo].[addDefaultToAgeFromCustomer]
AS
BEGIN
	SET NOCOUNT ON;
	--- add a default constraint
	ALTER TABLE Customer
	ADD CONSTRAINT df_Age
	DEFAULT 18 FOR Age
	PRINT('add a default constraint in Customer table in column Age')
END
GO

CREATE PROCEDURE [dbo].[removeDefaultToAgeFromCustomer]
AS
BEGIN
	SET NOCOUNT ON;

	-- remove a DEFAULT constraint
	ALTER TABLE Customer
	DROP CONSTRAINT df_Age
	PRINT('remove a default constraint in Customer table in column Age')
END
GO

---g)
CREATE PROCEDURE [dbo].[addCatalogue_Bouquets]
AS
BEGIN
	SET NOCOUNT ON;
	-- create a new table
	CREATE TABLE Catalogue_Bouquets(
		ID_CB INT NOT NULL,
		Name VARCHAR(30) NOT NULL,
		Price INT NOT NULL,
		Flower_ID int NOT NULL,
		CONSTRAINT PK_CB PRIMARY KEY(ID_CB)
	)
	PRINT('add a new table Cataloque_Bouquets')
END
GO

CREATE PROCEDURE [dbo].[removeCatalogue_Bouquets]
AS
BEGIN
	SET NOCOUNT ON;

	-- drop a table
	DROP TABLE Catalogue_Bouquets
	PRINT('remove a new table Cataloque_Bouquets')
END
GO

---d)
CREATE PROCEDURE [dbo].[addPKCatalogue]
AS
BEGIN
	SET NOCOUNT ON;
	---add primary key
	ALTER TABLE Catalogue_Bouquets
	DROP CONSTRAINT PK_CB
	ALTER TABLE Catalogue_Bouquets
	ADD CONSTRAINT PK_CB PRIMARY KEY(Name, Price);
	PRINT('add a primary key(Name, Price) in Catalogue_Bouquets ')
END
GO

CREATE PROCEDURE [dbo].[removePKCatalogue]
AS
BEGIN
	SET NOCOUNT ON;

	-- remove a primary key
	ALTER TABLE Catalogue_Bouquets
	DROP CONSTRAINT PK_CB;
	PRINT('remove primary key(Name, Price) in Catalogue_Bouquets ')
END
GO

---e)
CREATE PROCEDURE [dbo].[addCandidateFlower]
AS
BEGIN
	SET NOCOUNT ON;
	--- add a candidate key
	ALTER TABLE Flower
	ADD CONSTRAINT UC_Flower UNIQUE (Name, Color, Kind)
	PRINT('add a candidate key(Name, Color, Kind) in  Flower')
END
GO

CREATE PROCEDURE [dbo].[removeCandidateFlower]
AS
BEGIN
	SET NOCOUNT ON;
	-- remove a candidate key;
	ALTER TABLE Flower
	DROP CONSTRAINT UC_Flower
	PRINT('remove a candidate key(Name, Color, Kind) in  Flower')
END
GO


---f)
CREATE PROCEDURE [dbo].[addForeignKeyCB]
AS
BEGIN
	SET NOCOUNT ON;
	--- add foreign a constraint
	ALTER TABLE Catalogue_Bouquets
	ADD CONSTRAINT FK_CB FOREIGN KEY(Flower_ID) REFERENCES Flower(Flower_ID);
	PRINT('add a foreign key(Flower_id) in Catalogue_Bouquets')
END
GO

CREATE PROCEDURE [dbo].[removeForeignKeyCB]
AS
BEGIN
	SET NOCOUNT ON;
	-- remove a foreign key
	ALTER TABLE Catalogue_Bouquets
	DROP CONSTRAINT FK_CB;
	PRINT('remove the foreign key(Flower_id) in Catalogue_Bouquets')
END
GO

CREATE TABLE Versions(
	id_version INT
)

INSERT INTO Versions VALUES(1);
GO
SELECT id_version FROM Versions


CREATE TABLE version_change(
	version INT UNIQUE not null,
	procedure_current VARCHAR(max),
	procedure_backwards VARCHAR(MAX)
)

INSERT INTO version_change(version) VALUES(0)
INSERT INTO version_change VALUES(1, 'setDateFromOccasionChar','setDateFromOccasionDate')
INSERT INTO version_change VALUES(2, 'addAgeToCustomer','removeAgeToCustomer')
INSERT INTO version_change VALUES(3, 'addDefaultToAgeFromCustomer','removeDefaultToAgeFromCustomer')
INSERT INTO version_change VALUES(4, 'addCatalogue_Bouquets','removeCatalogue_Bouquets')
INSERT INTO version_change VALUES(5, 'addPKCatalogue','removePKCatalogue')
INSERT INTO version_change VALUES(6, 'addCandidateFlower','removeCandidateFlower')
INSERT INTO version_change VALUES(7, 'addForeignKeyCB','removeForeignKeyCB')

GO

CREATE PROCEDURE [dbo].[go_to_version]
	@newVersion INT
AS
BEGIN
	DECLARE @var varchar(max)
	DECLARE @currentVersion INT
	SELECT @currentVersion = id_version FROM Versions

	IF @newVersion > (SELECT MAX(version) FROM version_change) + 1 or @newVersion < 0
		BEGIN
			RAISERROR ('Bad version', 10, 1)	
		END
	ELSE
	BEGIN
		IF @currentVersion = @newVersion
		BEGIN
			RAISERROR('The version desired is the current version', 10, 1)
		END

		WHILE @currentVersion < @newVersion
		BEGIN
			SELECT @var=procedure_current from version_change where version=@currentVersion 
			EXEC(@var)
			SET @currentVersion = @currentVersion + 1
		END
	
		WHILE @currentVersion > @newVersion
		BEGIN
			SELECT @var = procedure_backwards from version_change where version=@currentVersion
			EXEC(@var)
			SET @currentVersion = @currentVersion-1
		END


		UPDATE Versions set id_version=@newVersion
	END
END
GO

execute go_to_version 8

SELECT * FROM version_change

SELECT id_version FROM Versions
UPDATE Versions set id_version = 7
GO

---adds
exec setDateFromOccasionChar
exec addAgeToCustomer
exec addDefaultToAgeFromCustomer
exec addCatalogue_Bouquets
exec addPKCatalogue
exec addCandidateFlower
exec addForeignKeyCB
---removes
exec removeForeignKeyCB
exec removeCandidateFlower
exec removePKCatalogue
exec removeCatalogue_Bouquets
exec removeDefaultToAgeFromCustomer
exec removeAgeToCustomer
exec setDateFromOccasionDate