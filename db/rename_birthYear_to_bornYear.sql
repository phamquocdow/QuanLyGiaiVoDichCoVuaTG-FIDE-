-- Rename tblPlayer.birthYear to tblPlayer.bornYear (SQL Server)
-- Execute this script against your database before running the updated application.

BEGIN TRANSACTION;

ALTER TABLE tblPlayer ADD bornYear INT;
UPDATE tblPlayer SET bornYear = birthYear;
ALTER TABLE tblPlayer DROP COLUMN birthYear;

COMMIT TRANSACTION;
