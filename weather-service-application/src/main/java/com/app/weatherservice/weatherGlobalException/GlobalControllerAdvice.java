package com.app.weatherservice.weatherGlobalException;

import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {


	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleMainElement(Exception noElelemt) {

		return new ResponseEntity<String>("EXCEPTION OCCURRED--"+noElelemt.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNoSuchElement(NoSuchElementException noElelemt) {

		return new ResponseEntity<String>("No data found", HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return new ResponseEntity<Object>("--Response Exception--"+"ex--"+ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleCommonRuntimeException(RuntimeException noElelemt) {

		return new ResponseEntity<String>("--Response Exception", HttpStatus.BAD_REQUEST);
	}

}
