package com.linh.pfa.common.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linh.pfa.common.dto.Currency;

@Service("commonService")
public class CommonServiceProxy {
	@Autowired
	private RestTemplate restTemplate;
	
    @Value("${pfa.endpoint.common-service}")
    private String commonServiceEndpoint;
	
	public Map<Long, BigDecimal> getExchangeRate() {
		Currency[] currencies = restTemplate.getForObject(commonServiceEndpoint + "/config/currencies", Currency[].class);
		Map<Long, BigDecimal> currencyMap = new HashMap<Long, BigDecimal>();
		Arrays.stream(currencies).forEach(x -> currencyMap.put(x.getId(), x.getExchangeRate()));
		return currencyMap;
	}
}
