-- PFA database design

-- Code Table
DROP TABLE IF EXISTS cfg_code_type;
CREATE TABLE cfg_code_type (
  id int NOT NULL AUTO_INCREMENT,
  code_type_name varchar(60) NOT NULL,
  code_type_desc varchar(100),
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cfg_code_table;
CREATE TABLE cfg_code_table (
  id int NOT NULL AUTO_INCREMENT,
  code_type_id varchar(20) NOT NULL,
  code_name varchar(20) NOT NULL,
  code_value varchar(100) NOT NULL,
  code_desc varchar(100),
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);
-- insert testing data
INSERT INTO cfg_code_type (id, code_type_name, created_by, created_date) VALUES(1, 'Transaction Type', 0, CURRENT_DATE);
INSERT INTO cfg_code_table (code_type_id, code_name, code_value, created_by, created_date) VALUES(1, 'BUY', 'Buy', 0, CURRENT_DATE);
INSERT INTO cfg_code_table (code_type_id, code_name, code_value, created_by, created_date) VALUES(1, 'SELL', 'Sell', 0, CURRENT_DATE);

-- Asset category configuration table
DROP TABLE IF EXISTS cfg_category;
CREATE TABLE cfg_category (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(20) NOT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(1, 'STOCK', 0, CURRENT_DATE);
INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(2, 'REIT', 0, CURRENT_DATE);
INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(3, 'BOND', 0, CURRENT_DATE);
INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(4, 'CASH', 0, CURRENT_DATE);
INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(5, 'CPF_ORDINARY', 0, CURRENT_DATE);
INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(6, 'CPF_MEDISAVE', 0, CURRENT_DATE);
INSERT INTO cfg_category (id, code, created_by, created_date) VALUES(7, 'CPF_SPECIAL', 0, CURRENT_DATE);

-- Exchange configuration table
DROP TABLE IF EXISTS cfg_exchange;
CREATE TABLE cfg_exchange (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO cfg_exchange (id, code, created_by, created_date) VALUES(1, 'SGX', 0, CURRENT_DATE);
INSERT INTO cfg_exchange (id, code, created_by, created_date) VALUES(2, 'CN', 0, CURRENT_DATE);
INSERT INTO cfg_exchange (id, code, created_by, created_date) VALUES(3, 'NYSE', 0, CURRENT_DATE);
INSERT INTO cfg_exchange (id, code, created_by, created_date) VALUES(4, 'HKEX', 0, CURRENT_DATE);
INSERT INTO cfg_exchange (id, code, created_by, created_date) VALUES(5, 'NASDAQ', 0, CURRENT_DATE);

-- Currency configuration table
DROP TABLE IF EXISTS cfg_currency;
CREATE TABLE cfg_currency (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  exchange_rate decimal(10,2) NOT NULL, -- yesterday exchange rate updated by scheduled job
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO cfg_currency (id, code, exchange_rate, created_by, created_date) VALUES(1, 'SGD', 1, 0, CURRENT_DATE);
INSERT INTO cfg_currency (id, code, exchange_rate, created_by, created_date) VALUES(2, 'RMB', 0.2, 0, CURRENT_DATE);
INSERT INTO cfg_currency (id, code, exchange_rate, created_by, created_date) VALUES(3, 'USD', 1.4, 0, CURRENT_DATE);
INSERT INTO cfg_currency (id, code, exchange_rate, created_by, created_date) VALUES(4, 'HKD', 0.18, 0, CURRENT_DATE);

DROP TABLE IF EXISTS cfg_transaction_type;
CREATE TABLE cfg_transaction_type (
  id int NOT NULL AUTO_INCREMENT,
  code varchar(10) NOT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

INSERT INTO cfg_transaction_type (id, code, created_by, created_date) VALUES(1, 'BUY', 0, CURRENT_DATE);
INSERT INTO cfg_transaction_type (id, code, created_by, created_date) VALUES(2, 'SELL', 0, CURRENT_DATE);

-- Asset Account (all asset must be in this table)
DROP TABLE IF EXISTS account;
CREATE TABLE account (
  id int NOT NULL AUTO_INCREMENT,
  institute_name varchar(20) NOT NULL,
  account_no varchar(20),
  account_holder varchar(50),
  category_id varchar(10) NOT NULL,
  currency_id int NOT NULL,
  amount decimal(10,2) NOT NULL,
  maturity_date datetime,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
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
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
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
  latest_price decimal(7,3) NOT NULL, -- yesterday price updated by scheduled job or current price by user-triggered manually refresh
  category_id int NOT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS stock_transaction;
CREATE TABLE stock_transaction (
  id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  transaction_date datetime NOT NULL,
  transaction_type_id int NOT NULL,
  price decimal(7,3) NOT NULL,
  quantity int DEFAULT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS portfolio;
CREATE TABLE portfolio (
  id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  quantity int DEFAULT NULL,
  cost decimal(7,3) DEFAULT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dividend;
CREATE TABLE dividend (
  id int NOT NULL AUTO_INCREMENT,
  stock_id int NOT NULL,
  amount decimal(7,2) NOT NULL,
  pay_date datetime NOT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS profit;
CREATE TABLE profit (
  id int NOT NULL AUTO_INCREMENT,
  Stock_id int NOT NULL,
  amount decimal(8,2) NOT NULL,
  created_by int NOT NULL,
  created_date datetime NOT NULL,
  updated_by int,
  updated_date datetime,
  is_deleted bit DEFAULT 0,
  PRIMARY KEY (id)
);
