-- PFA database design

-- Code Table
DROP TABLE IF EXISTS code_type;
CREATE TABLE code_type (
  id int NOT NULL AUTO_INCREMENT,
  code_type_name varchar(60) NOT NULL,
  code_type_desc varchar(100),
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS code_table;
CREATE TABLE code_table (
  id int NOT NULL AUTO_INCREMENT,
  code_type_id varchar(20) NOT NULL,
  code_name varchar(20) NOT NULL,
  code_value varchar(100) NOT NULL,
  code_desc varchar(100),
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);
-- insert testing data
INSERT INTO code_type (id, code_type_name, created_by, created_date, is_deleted) VALUES(1, 'Transaction Type', 0, CURRENT_DATE, 0);
INSERT INTO code_table (code_type_id, code_name, code_value, created_by, created_date, is_deleted) VALUES(1, 'BUY', 'Buy', 0, CURRENT_DATE, 0);
INSERT INTO code_table (code_type_id, code_name, code_value, created_by, created_date, is_deleted) VALUES(1, 'SELL', 'Sell', 0, CURRENT_DATE, 0);

-- Asset category configuration table
DROP TABLE IF EXISTS asset_category;
CREATE TABLE asset_category (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(20) NOT NULL,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(1, 'STOCK', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(2, 'REIT', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(3, 'BOND', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(4, 'CASH', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(5, 'CPF_ORDINARY', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(6, 'CPF_MEDISAVE', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(7, 'CPF_SPECIAL', 0, CURRENT_DATE, 0);

-- Exchange configuration table
DROP TABLE IF EXISTS exchange;
CREATE TABLE exchange (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(1, 'SGX', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(2, 'CN', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(3, 'NYSE', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(4, 'HKEX', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(5, 'NASDAQ', 0, CURRENT_DATE, 0);

-- Currency configuration table
DROP TABLE IF EXISTS currency;
CREATE TABLE currency (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  exchange_rate decimal(10,2) NOT NULL, -- yesterday exchange rate updated by scheduled job
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(1, 'SGD', 1, 0, CURRENT_DATE, 0);
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(2, 'RMB', 0.2, 0, CURRENT_DATE, 0);
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(3, 'USD', 1.4, 0, CURRENT_DATE, 0);
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(4, 'HKD', 0.18, 0, CURRENT_DATE, 0);

DROP TABLE IF EXISTS action;
CREATE TABLE action (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO action (id, code, created_by, created_date, is_deleted) VALUES(1, 'BUY', 0, CURRENT_DATE, 0);
INSERT INTO action (id, code, created_by, created_date, is_deleted) VALUES(2, 'SELL', 0, CURRENT_DATE, 0);

-- Asset Account (all asset must be in this table)
DROP TABLE IF EXISTS account;
CREATE TABLE account (
  id int NOT NULL AUTO_INCREMENT,
  institute_name varchar(20) NOT NULL,
  account_no varchar(20),
  account_holder varchar(50),
  category_id int NOT NULL,
  currency_id int NOT NULL,
  amount decimal(10,2) NOT NULL,
  maturity_date date,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

-- Asset History
DROP TABLE IF EXISTS asset_history;
CREATE TABLE asset_history (
  id int NOT NULL AUTO_INCREMENT,
  record_date datetime NOT NULL,	-- same value for all category within the date
  category_id varchar(10) NOT NULL,
  amount decimal(10,2) NOT NULL, -- in SGD
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS portfolio;
CREATE TABLE portfolio (
  id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  quantity int DEFAULT NULL,
  cost decimal(7,3) DEFAULT NULL,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

-- Stock list
DROP TABLE IF EXISTS stock;
CREATE TABLE stock (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  name varchar(60) NOT NULL,
  exchange_id int NOT NULL,
  currency_id int NOT NULL,
  latest_price decimal(7,3), -- yesterday price updated by scheduled job or current price by user-triggered manually refresh
  category_id int NOT NULL,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
  id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  transaction_date datetime NOT NULL,
  action_id int NOT NULL,
  price decimal(7,3) NOT NULL,
  quantity int DEFAULT NULL,
  is_reversed bit DEFAULT 0,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dividend;
CREATE TABLE dividend (
  id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  amount decimal(7,2) NOT NULL,
  pay_date datetime NOT NULL,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS profit;
CREATE TABLE profit (
  id int NOT NULL AUTO_INCREMENT,
  Stock_id int NOT NULL,
  realized decimal(8,2) NOT NULL DEFAULT 0,
  dividend decimal(8,2) NOT NULL DEFAULT 0,
  created_by int NOT NULL DEFAULT 0,
  created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by int,
  updated_date timestamp NULL DEFAULT NULL,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);
