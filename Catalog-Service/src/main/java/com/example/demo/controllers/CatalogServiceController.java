package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Book;
import com.example.demo.services.BookServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class CatalogServiceController {

	private BookServiceImpl service;

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ofNullable(service.findAll());
	}

	@GetMapping("/{isbn}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Book> findByIsbn(@PathVariable String isbn) {
		return ResponseEntity.of(service.findByIsbn(isbn));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Book> save(@RequestBody @Valid Book book) {
		if (service.existsByIsbn(book.isbn()))
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		else
			return ResponseEntity.ofNullable(service.save(book));
	}

	@PatchMapping("/{isbn}")
	public ResponseEntity<Book> update(@PathVariable String isbn, @RequestBody JsonPatch patchToApply)
			throws JsonProcessingException, JsonPatchException {
		if (service.existsByIsbn(isbn)) {
			var existingBookDetails = service.findByIsbn(isbn).get();
			var updateBookDetails = service.applyPatch(existingBookDetails, patchToApply);
			return ResponseEntity.status(HttpStatus.OK).body(updateBookDetails);
		} else {
			Book aBook = new Book("", "", "", 0.0);
			var newBookDetails = service.applyPatch(aBook, patchToApply);
			return ResponseEntity.status(HttpStatus.OK).body(service.save(newBookDetails));
		}
	}

	@DeleteMapping("/{isbn}")
	public ResponseEntity<?> deleteByIsbn(@PathVariable String isbn) {
		service.deleteByIsbn(isbn);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
