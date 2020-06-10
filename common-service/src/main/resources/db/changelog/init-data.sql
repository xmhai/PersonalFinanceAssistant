--liquibase formatted sql
--changeset hai:1
-- asset_category
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(1, 'STOCK', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(2, 'REIT', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(3, 'BOND', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(4, 'CASH', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(5, 'CPF_ORDINARY', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(6, 'CPF_MEDISAVE', 0, CURRENT_DATE, 0);
INSERT INTO asset_category (id, code, created_by, created_date, is_deleted) VALUES(7, 'CPF_SPECIAL', 0, CURRENT_DATE, 0);
-- exchange
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(1, 'SGX', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(2, 'CN', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(3, 'NYSE', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(4, 'HKEX', 0, CURRENT_DATE, 0);
INSERT INTO exchange (id, code, created_by, created_date, is_deleted) VALUES(5, 'NASDAQ', 0, CURRENT_DATE, 0);
-- currency
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(1, 'SGD', 1, 0, CURRENT_DATE, 0);
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(2, 'RMB', 0.2, 0, CURRENT_DATE, 0);
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(3, 'USD', 1.4, 0, CURRENT_DATE, 0);
INSERT INTO currency (id, code, exchange_rate, created_by, created_date, is_deleted) VALUES(4, 'HKD', 0.18, 0, CURRENT_DATE, 0);
-- action
INSERT INTO action (id, code, created_by, created_date, is_deleted) VALUES(1, 'BUY', 0, CURRENT_DATE, 0);
INSERT INTO action (id, code, created_by, created_date, is_deleted) VALUES(2, 'SELL', 0, CURRENT_DATE, 0);

INSERT INTO job_config (id,created_by,created_date,is_deleted,updated_by,updated_date,cron_expression,description,end_time,job_class_name,name,start_time) 
     VALUES (1,0,'2020-06-02 00:00:00',0,0,'2020-06-06 08:32:47','0 0 18 ? * MON-FRI','Update Stock Price Every day on 6pm','2022-07-31 16:00:00','com.linh.pfa.job.stock.PriceUpdateJob','updateprice','2020-06-01 00:00:00');
