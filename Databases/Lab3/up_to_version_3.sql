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
CREATE PROCEDURE [dbo].[up_to_version_3]
	-- Add the parameters for the stored procedure here

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--- add a default constraint
	ALTER TABLE Supplier
	ADD CONSTRAINT df_City 
	DEFAULT 'Cluj' FOR City
	print('up_to_version_3 - set the default value Cluj for each new entry City column')

END
GO
