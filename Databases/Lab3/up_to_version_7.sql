USE[FlowerShop]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[up_to_version_7]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	-- create a new table
	CREATE TABLE Catalogue_Bouquets(
	CB_ID INT NOT NULL PRIMARY KEY,
	Name VARCHAR(30) NOT NULL,
	Price INT NOT NULL)
	print('up_to_version_7 - create table Cataloque Bouquets');
END
GO
