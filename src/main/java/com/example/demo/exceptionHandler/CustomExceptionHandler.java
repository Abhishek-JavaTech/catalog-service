package com.example.demo.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	
	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleException(MethodArgumentNotValidException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide correct details, got error as this -" + ex.getLocalizedMessage());
	}
	
	
	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<?> handleException(Exception ex){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getLocalizedMessage());
	}
	
	
}
