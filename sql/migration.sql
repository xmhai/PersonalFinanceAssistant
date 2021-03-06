-- account
TRUNCATE TABLE account;
INSERT INTO account (
	institute_name,
	account_no,
	account_holder,
	category_id,
	currency_id,
	amount,
	maturity_date,
	created_by,
	created_date,
    is_deleted
)
SELECT 
    BankName,
    BankAccountNo,
    BankAccountName,
    4, -- CASH
    Currency + 1,
    Amount,
    MaturityDate,
    0,
    CURRENT_DATE,
    0
FROM asset.cash;

-- asset_history
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
	created_date,
	is_deleted
)
SELECT stock.ID,
    stock.Code,
    stock.Name,
    stock.Exchange + 1,
    stock.Currency + 1,
    stock.Category + 1,
    stock.price,
    0,
    CURRENT_DATE,
    0
FROM asset.stock;
update stock set code = left(code, instr(code, '.')-1) where instr(code, '.')>0 and right(code, 2)<>'UN';

ALTER TABLE stock AUTO_INCREMENT = 100;

-- stock_transaction
TRUNCATE TABLE transaction;
INSERT INTO transaction (
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
SELECT transaction.StockID,
    transaction.OrderDate,
    transaction.Action + 1,
    transaction.Price,
    transaction.Quantity,
    0,
    0,
    CURRENT_DATE,
    0
FROM asset.transaction;

-- portfolio
TRUNCATE TABLE portfolio;
INSERT INTO portfolio (
  stock_id,
  quantity,
  cost,
  created_by,
  created_date,
  is_deleted
)
SELECT portfolio.StockID,
    portfolio.Quantity,
    portfolio.Cost,
    0,
    CURRENT_DATE,
    0
FROM asset.portfolio;

-- dividend
TRUNCATE TABLE dividend;
INSERT INTO dividend (
  stock_id,
  amount,
  pay_date,
  created_by,
  created_date,
  is_deleted
)
SELECT divident.StockID,
    divident.Amount,
    divident.PayDate,
    0,
    CURRENT_DATE,
    0
FROM asset.divident;

-- change amount to SGD
drop view if exists vw_stock_exchange_rate;
create view vw_stock_exchange_rate (stock_id, exchange_rate)
as
select s.id, exchange_rate from stock s, currency c where s.currency_id = c.id;

update dividend set amount = amount * (select exchange_rate from vw_stock_exchange_rate where stock_id = dividend.stock_id);

-- profit
TRUNCATE TABLE profit;
INSERT INTO profit (
  stock_id,
  realized,
  dividend,
  created_by,
  created_date,
  is_deleted
)
SELECT profit.StockID,
    profit.Amount,
    0,
    0,
    CURRENT_DATE,
    0
FROM asset.profit;

update profit set realized = realized * (select exchange_rate from vw_stock_exchange_rate where stock_id = profit.stock_id);

-- insert records from dividend table
update profit p set dividend = (select sum(amount) from dividend d where d.stock_id=p.stock_id);
update profit set dividend = 0 where dividend is null;
INSERT INTO profit (
  Stock_id,
  realized,
  dividend,
  created_by,
  created_date,
  is_deleted
)
select stock_id,
	0,
	sum(amount),
    0,
    CURRENT_DATE,
    0
  from dividend where stock_id not in (select stock_id from profit) group by stock_id;
-- insert records from portfolio table
INSERT INTO profit (
  Stock_id,
  realized,
  dividend,
  created_by,
  created_date,
  is_deleted
)
select stock_id,
	0,
	0,
    0,
    CURRENT_DATE,
    0
  from portfolio where stock_id not in (select stock_id from profit) group by stock_id;

-- calculate monthly expense
select * from bank_transaction
 where description not like 'UOB KAY HIAN%'
   and description not like 'UOB:4203035572%'
   and description not like 'FAST PAYMENT%'
   and description not like 'Funds Trf - FAST%'
   and debit>0
 order by transaction_date desc;

-- expense group by month 
 select month(transaction_date) as mth, sum(debit) from bank_transaction
 where description not like 'UOB KAY HIAN%'
   and description not like 'UOB:4203035572%'
   and description not like 'FAST PAYMENT%'
   and description not like 'Funds Trf - FAST%'
   and debit>0
 group by month(transaction_date)
 order by mth;
