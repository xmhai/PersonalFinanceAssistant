package com.linh.common.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Exception handling
// For business logic error which we can show user the detailed error message
//     response code is 500, and the error message is defined our program
//For program error, e.g. null pointer
//    response code is 500, and the error message should not be sent to front end, instead only display system error
//For system error, e.g. database operation failure
//    response code is 500, and the error message should not be sent to front end, instead only display system error
@ControllerAdvice("com.linh")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	// Business logic exception handling
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleBusinessException(final BusinessException e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	// Generaic exception handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(final Exception e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>("System Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
