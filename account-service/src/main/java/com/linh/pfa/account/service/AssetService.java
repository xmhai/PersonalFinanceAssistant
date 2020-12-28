package com.linh.pfa.account.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linh.pfa.account.entity.Account;
import com.linh.pfa.account.entity.AccountRepository;
import com.linh.pfa.account.entity.AssetHistory;
import com.linh.pfa.account.entity.AssetHistoryRepository;
import com.linh.pfa.common.enums.Category;
import com.linh.pfa.common.service.CommonServiceProxy;

@Service
public class AssetService {
	@Autowired
	private AccountRepository accountRespository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonServiceProxy commonService;
	
	@Autowired
	private AssetHistoryRepository assetHistoryRespository;
	
    @Value("${pfa.endpoint.stock-service}")
    private String stockServiceEndpoint;

	public List<Map<String, Object>> getAllocation() throws Exception {
		Map<Long, BigDecimal> currencies = commonService.getExchangeRate();
		
		// get account allocation (except stocks)
		List<Account> accounts = accountRespository.findAll();
		double stockAmt = 0; 
		double bondAmt = 0; 
		double reitAmt = 0; 
		double cashAmt = 0; 
		for (Account account : accounts) {
			Long currencyId = Long.valueOf(account.getCurrency().getValue());
			double amtSGD = account.getAmount().doubleValue() * currencies.get(currencyId).doubleValue();
			if (account.getCategory()==Category.STOCKS) {
				stockAmt = stockAmt + amtSGD;
			} else if (account.getCategory()==Category.BONDS) {
				bondAmt = bondAmt + amtSGD;
			} else if (account.getCategory()==Category.REITS) {
				reitAmt = reitAmt + amtSGD;
			} else if (account.getCategory()==Category.CASH) {
				cashAmt = cashAmt + amtSGD;
			}
		}
		
		// retrieve stocks allocation
		String response = restTemplate.getForObject(stockServiceEndpoint + "/portfolios/allocation", String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response, Map.class);
		stockAmt = stockAmt + Double.valueOf(map.get(Category.STOCKS.name()).toString());
		bondAmt = bondAmt + Double.valueOf(map.get(Category.BONDS.name()).toString());
		reitAmt = reitAmt + Double.valueOf(map.get(Category.REITS.name()).toString());
		
		double totalAmt = stockAmt + bondAmt + reitAmt + cashAmt;  
		
		List result = new ArrayList<>();
		addCategory(result, Category.CASH.name(), cashAmt, cashAmt/totalAmt);
		addCategory(result, Category.BONDS.name(), bondAmt, bondAmt/totalAmt);
		addCategory(result, Category.REITS.name(), reitAmt, reitAmt/totalAmt);
		addCategory(result, Category.STOCKS.name(), stockAmt, stockAmt/totalAmt);

		return result;
	}
	
	private void addCategory(List result, String category, double amt, double percentage) {
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("category", category);
		item.put("amount", amt);
		item.put("percentage", percentage);
		result.add(item);
	}
	
	@Transactional
	public void saveHistory() throws Exception {
		// delete any existing record
		assetHistoryRespository.deleteByRecordDate(LocalDate.now());
		
		// insert new records
		AssetHistory assetHistory = new AssetHistory();
		List<Map<String, Object>> allocations = getAllocation();
		double totalAmt = 0; 
		for (Map<String, Object> allocation : allocations) {
			Category category = Category.valueOf((String)allocation.get("category"));
			double amt = (double)allocation.get("amount");
			BigDecimal decAmt = new BigDecimal(amt);
			totalAmt = totalAmt + amt; 
			if (category==Category.STOCKS) {
				assetHistory.setStocks(decAmt);;
			} else if (category==Category.BONDS) {
				assetHistory.setBonds(decAmt);;
			} else if (category==Category.REITS) {
				assetHistory.setReits(decAmt);;
			} else if (category==Category.CASH) {
				assetHistory.setCash(decAmt);;
			}
			
		}
		
		assetHistory.setRecordDate(LocalDate.now());
		assetHistory.setTotal(new BigDecimal(totalAmt));;
		assetHistoryRespository.save(assetHistory);
	}
	
	public List getHistories() {
		return assetHistoryRespository.findAll(Sort.by(Sort.Direction.DESC, "recordDate"));
	}
}
