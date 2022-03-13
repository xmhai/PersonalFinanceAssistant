package com.linh.pfa.job.stock;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableBatchProcessing
@SpringBootApplication
public class StockPriceJobApplication {

	public static void main(String[] args) {
		SpringApplication.exit(SpringApplication.run(StockPriceJobApplication.class, args));
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step() {
		return this.stepBuilderFactory.get("stepUpdateStockPrice")
				.<StockPriceUpdate, StockPriceUpdate>chunk(5)
				.reader(portfolioItemReader(null, null))
				.processor(itemProcessor())
				.writer(stockItemWriter(null))
				.build();
	}

	@Bean
	@StepScope
	public JdbcPagingItemReader<StockPriceUpdate> portfolioItemReader(DataSource dataSource, PagingQueryProvider queryProvider) {
		return new JdbcPagingItemReaderBuilder<StockPriceUpdate>()
				.name("portfolioItemReader")
				.dataSource(dataSource)
				.queryProvider(queryProvider)
				.pageSize(10)
				.rowMapper(new StockPriceUpdateRowMapper())
				.build();
	}

	@Bean
	public SqlPagingQueryProviderFactoryBean pagingQueryProvider(DataSource dataSource) {
		SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
		factoryBean.setDataSource(dataSource);
		// SELECT * FROM portfolio where realized_price is null or realized_price = 0
		factoryBean.setSelectClause("select stock_id");
		factoryBean.setFromClause("from portfolio");
		factoryBean.setWhereClause("where realized_price is null or realized_price = 0");
		factoryBean.setSortKey("stock_id");
		return factoryBean;
	}

	@Bean
	public StockPriceUpdateItemProcessor itemProcessor() {
		return new StockPriceUpdateItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<StockPriceUpdate> stockItemWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<StockPriceUpdate>()
				.dataSource(dataSource)
				.sql("UPDATE stock SET latest_price=:latestPrice, updated_date=NOW(), updated_by=999 WHERE id=:id")
				.beanMapped()
				.build();
	}
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("job")
				.start(step())
				.incrementer(new RunIdIncrementer())
				.listener(new JobLoggerListener())
				.build();
	}
}
