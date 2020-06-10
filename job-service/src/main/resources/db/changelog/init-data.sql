--liquibase formatted sql
--changeset job-hai:1
INSERT INTO job_config (id,created_by,created_date,is_deleted,updated_by,updated_date,cron_expression,description,end_time,job_class_name,name,start_time) 
     VALUES (1,0,'2020-06-02 00:00:00',0,0,'2020-06-06 08:32:47','0 0 18 ? * MON-FRI','Update Stock Price Every day on 6pm','2022-07-31 16:00:00','com.linh.pfa.job.stock.PriceUpdateJob','updateprice','2020-06-01 00:00:00');
