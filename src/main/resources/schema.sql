CREATE DATABASE demo;
GO

USE demo;
GO

CREATE TABLE customer (id int IDENTITY(1,1)  PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255));
GO
