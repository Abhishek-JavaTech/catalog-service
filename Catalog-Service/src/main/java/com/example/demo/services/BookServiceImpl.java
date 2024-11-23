package com.example.demo.services;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.demo.models.Book;
import com.example.demo.repos.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

@Service
public class BookServiceImpl implements BookRepository {

	private final Map<String, Book> books = new ConcurrentHashMap<>();

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Iterable<Book> findAll() {
		return books.values();
	}

	@Override
	public Optional<Book> findByIsbn(String isbn) {
		return Optional.of(books.get(isbn));
	}

	@Override
	public boolean existsByIsbn(String isbn) {
		return books.containsKey(isbn);
	}

	@Override
	public Book save(Book book) {
		books.put(book.isbn(), book);
		return books.get(book.isbn());
	}

	@Override
	public void deleteByIsbn(String isbn) {
		if (existsByIsbn(isbn))
			books.remove(isbn);
	}

	public Book applyPatch(Book book, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
		JsonNode jsonNode = patch.apply(objectMapper.convertValue(book, JsonNode.class));
		return objectMapper.treeToValue(jsonNode, Book.class);
	}

}
