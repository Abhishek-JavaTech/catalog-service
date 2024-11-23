package com.example.demo.models;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@TestInstance(Lifecycle.PER_CLASS)
class BookTest {

	Validator validator;

	@BeforeAll
	void test() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	@DisplayName("validate books for validations applied")
	void validateBook() {
		var newBook = new Book("", null, "", 1.0);
		var result = validator.validate(newBook);
		assertTrue(result.size() > 0);

		var newBook2 = new Book(null, "122345452535", "", 1.0);
		result = validator.validate(newBook2);
		assertTrue(result.size() > 0);

		var newBook3 = new Book("", "", null, 1.0);
		result = validator.validate(newBook3);
		assertTrue(result.size() > 0);

		var newBook4 = new Book("", "", "", -1.0);
		result = validator.validate(newBook4);
		assertTrue(result.size() > 0);

		var newBook5 = new Book("", "", "", 1.0);
		result = validator.validate(newBook5);
		assertTrue(result.size() == 0);
	}

}
