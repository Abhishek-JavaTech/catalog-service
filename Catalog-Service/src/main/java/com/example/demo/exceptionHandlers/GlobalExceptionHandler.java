package com.example.demo.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = JsonPatchException.class)
	public ResponseEntity<?> exception(JsonPatchException exception) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage());

	}

	@ExceptionHandler(exception = JsonProcessingException.class)
	public ResponseEntity<?> exception(JsonProcessingException exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage());
	}

	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<?> exception(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("CommonException: " + exception.getLocalizedMessage());
	}
}
