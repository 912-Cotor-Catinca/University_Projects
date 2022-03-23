USE [FlowerShop];
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
CREATE PROCEDURE [dbo].[up_to_version_6]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--- add foreign a constraint
	ALTER TABLE Orders
	ADD CONSTRAINT fk_Orders FOREIGN KEY(Flower_ID) REFERENCES Flower(Flower_ID);
	print('up_to_version_6 - create a foreign key from Flower (Flower_ID) in Orders table ')
END
GO
