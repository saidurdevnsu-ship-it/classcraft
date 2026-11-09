-- Creates the eduapp database and sets up the root/root login for it.
-- Run once as a MySQL admin:
--   mysql -u root -p < db/setup.sql

CREATE DATABASE IF NOT EXISTS eduapp
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 'root'@'localhost' normally already exists, so create it only if missing,
-- then (re)set its password to 'root'.
CREATE USER IF NOT EXISTS 'root'@'localhost' IDENTIFIED BY 'root';
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';

GRANT ALL PRIVILEGES ON eduapp.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
