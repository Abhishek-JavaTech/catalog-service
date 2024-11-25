package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Books;

@Repository
public interface CatalogRepository extends CrudRepository<Books, Long> {

	Books findByIsbn(String isbn);

	void deleteByIsbn(String isbn);

}
