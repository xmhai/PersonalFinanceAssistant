package com.linh.common.base;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		LocalDateTime startTime = LocalDateTime.now(); 
		logger.info("{} {}", req.getMethod(), req.getRequestURI());
		
		chain.doFilter(request, response);
		
		logger.info("Response: {} {} {} ms", req.getRequestURI(), res.getStatus(), Duration.between(startTime, LocalDateTime.now()).toMillis());
	}
}