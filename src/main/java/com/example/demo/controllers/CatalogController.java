package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Books;
import com.example.demo.services.CatalogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class CatalogController {

	@Value("${message.text}")
	private String messageText;
	private final CatalogService service;

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<?> get(@PathVariable("isbn") String isbn) {
		return ResponseEntity.status(HttpStatus.OK).body(service.get(isbn));
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody @Valid Books book) {
		service.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(get(book.getIsbn()).getBody());
	}

	@PutMapping("/{isbn}")
	public ResponseEntity<?> put(@RequestBody @Valid Books book, @PathVariable("isbn") String isbn) {
		return ResponseEntity.status(HttpStatus.OK).body(service.put(isbn, book));
	}

	@PatchMapping("/{isbn}")
	public ResponseEntity<?> patch(@RequestBody @Valid JsonPatch jsonPatch, @PathVariable("isbn") String isbn)
			throws JsonProcessingException, IllegalArgumentException, JsonPatchException {
		return ResponseEntity.status(HttpStatus.OK).body(service.patch(isbn, jsonPatch));
	}

	@DeleteMapping("/{isbn}")
	public ResponseEntity<?> delete(@PathVariable("isbn") String isbn) {
		service.delete(isbn);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
