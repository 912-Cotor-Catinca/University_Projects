USE[FlowerShop]
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[up_to_version_5]
	-- Add the parameters for the stored procedure here
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--- add a candidate key
	ALTER TABLE Customer
	ADD CONSTRAINT UC_Customer UNIQUE (City, Country);
	print('up_to_version_5 - create a candidate key (City, Country) in Customer table ')
END
GO
