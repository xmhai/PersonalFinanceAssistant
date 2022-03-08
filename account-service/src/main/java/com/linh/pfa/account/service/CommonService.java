package com.linh.pfa.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.linh.pfa.api.common.Currency;

import static org.springframework.http.HttpMethod.GET;

@Service
public class CommonService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${pfa.endpoint.common-service}")
	private String commonServiceUrl;

	public Map<Long, BigDecimal> getExchangeRate() {
		List<Currency> list = restTemplate.exchange(commonServiceUrl, GET, null,
				new ParameterizedTypeReference<List<Currency>>() {}).getBody();

	    Map<Long, BigDecimal> currencies = list.stream()
	    	      .collect(Collectors.toMap(Currency::getId, Currency::getExchangeRate));
	    
        return currencies;
	}

}
