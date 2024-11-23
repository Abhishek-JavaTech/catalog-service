package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controllers.CatalogServiceController;
import com.example.demo.models.Book;
import com.example.demo.services.BookServiceImpl;

@WebMvcTest(controllers = CatalogServiceController.class)
class CatalogServiceApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BookServiceImpl bookServiceImpl;

	@Test
	void checkSaveAPI() throws Exception {

		Book savedBook = new Book("Effective Java", "123456789", "Joshua Bloch", 45.99);
		Mockito.when(bookServiceImpl.save(Mockito.any())).thenReturn(savedBook);

		mockMvc.perform(post("/books").content("""
				{
					"name": "Clean Code",
				               "isbn": "987654321",
				               "author": "Robert C. Martin",
				               "price": 39.99
				}
				""").contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(status().is2xxSuccessful());

	}

}
