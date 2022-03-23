USE [FlowerShop]
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
CREATE PROCEDURE [dbo].[down_to_version_2]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	-- remove a DEFAULT constraint
	ALTER TABLE Supplier
	DROP CONSTRAINT df_City
	print('down_to_version_2 - remove the default value Cluj for each new entry City column');
END
GO
