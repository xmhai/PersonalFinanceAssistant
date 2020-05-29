-- account
TRUNCATE TABLE account;
INSERT INTO account (
  institute_name,
  account_no,
  account_holder,
  category_id,
  currency_id,
  amount,
  maturity_date
)
SELECT 
    `cash`.`BankName`,
    `cash`.`BankAccountNo`,
    `cash`.`BankAccountName`,
    4, -- CASH
    `cash`.`Currency` + 1,
    `cash`.`Amount`,
    `cash`.`MaturityDate`
FROM `asset`.`cash`;

ALTER TABLE account AUTO_INCREMENT = 20;

TRUNCATE TABLE asset_history;
INSERT INTO asset_history (
	record_date,
	bonds,
	cash,
	cpf_medisave,
	cpf_ordinary,
	cpf_special,
	reits,
	stocks,
	total,
	created_by,
	created_date,
    is_deleted
)
SELECT
	recorddate,
	bond,
	cash,
	cpfmedisave,
	cpfordinary,
	cpfspecial,
	reits,
	stock,
	bond + cash + reits + stock,
	0,
	recorddate,
    0
FROM asset.assethistory;

-- stock
TRUNCATE TABLE stock;
INSERT INTO stock (
  id,
  code,
  name,
  exchange_id,
  currency_id,
  category_id,
  latest_price,
  created_by,
  created_date
)
SELECT `stock`.`ID`,
    `stock`.`Code`,
    `stock`.`Name`,
    `stock`.`Exchange` + 1,
    `stock`.`Currency` + 1,
    `stock`.`Category` + 1,
    `stock`.`price`,
    0,
    CURRENT_DATE
FROM `asset`.`stock`;

-- stock_transaction
TRUNCATE TABLE transaction;
INSERT INTO transaction (
  id,
  stock_id,
  transaction_date,
  action_id,
  price,
  quantity,
  portfolio_id,
  created_by,
  created_date,
  is_deleted
)
SELECT `transaction`.`ID`,
    `transaction`.`StockID`,
    `transaction`.`OrderDate`,
    `transaction`.`Action` + 1,
    `transaction`.`Price`,
    `transaction`.`Quantity`,
    0,
    0,
    CURRENT_DATE,
    0
FROM `asset`.`transaction`;

-- portfolio
TRUNCATE TABLE portfolio;
INSERT INTO portfolio (
  id,
  stock_id,
  quantity,
  cost,
  created_by,
  created_date,
  is_deleted
)
SELECT `portfolio`.`ID`,
    `portfolio`.`StockID`,
    `portfolio`.`Quantity`,
    `portfolio`.`Cost`,
    0,
    CURRENT_DATE,
    0
FROM `asset`.`portfolio`;

-- dividend
TRUNCATE TABLE dividend;
INSERT INTO dividend (
  id,
  stock_id,
  amount,
  pay_date,
  created_by,
  created_date,
  is_deleted
)
SELECT `divident`.`ID`,
    `divident`.`StockID`,
    `divident`.`Amount`,
    `divident`.`PayDate`,
    0,
    CURRENT_DATE,
    0
FROM `asset`.`divident`;

-- profit
TRUNCATE TABLE profit;
INSERT INTO profit (
  id,
  Stock_id,
  amount,
  created_by,
  created_date,
  is_deleted
)
SELECT `profit`.`ID`,
    `profit`.`StockID`,
    `profit`.`Amount`,
    0,
    CURRENT_DATE,
    0
FROM `asset`.`profit`;

