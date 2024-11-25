package com.example.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Books;
import com.example.demo.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogService {

	private final ObjectMapper objectMapper;
	private final CatalogRepository repo;

	public void save(Books book) {
		repo.save(book);
	}

	public Iterable<Books> getAll() {
		return repo.findAll();
	}

	public Optional<Books> get(String isbn) {
		return Optional.ofNullable(repo.findByIsbn(isbn));
	}

	public Optional<Books> put(String isbn, Books bookToUpdate) {
//		var bookToUpdate = get(isbn).get();
//		mapper.map(book, bookToUpdate);
		return Optional.of(repo.save(bookToUpdate));
	}

	public Optional<Books> patch(String isbn, JsonPatch bookToPatch)
			throws IllegalArgumentException, JsonPatchException, JsonProcessingException {
		var bookToUpdate = get(isbn).get();

		JsonNode jsonNode = bookToPatch.apply(objectMapper.convertValue(bookToUpdate, JsonNode.class));
		var updatedBook = objectMapper.treeToValue(jsonNode, Books.class);
		repo.save(updatedBook);
		return Optional.of(updatedBook);
	}

	public void delete(String isbn) {
		repo.deleteByIsbn(isbn);
	}

}
