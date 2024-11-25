package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.demo.models.Books;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class CatalogServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Container
	@ServiceConnection
	private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest").withDatabaseName("test")
			.withUsername("test").withPassword("test");

	@Test
	@Disabled
	void isStarted() {
		mySQLContainer.isRunning();
	}

	@Test
	@DisplayName("saving book")
	void testSave() {

		String request = """
										{
  "name": "Spring-JPA",
  "isbn": "0231234-4566",
  "author": "Abhishek Rangari",
  "price": 25.0
}
								""";
		
		Books bookToSave = new Books();
		bookToSave.setAuthor("Abhishek Rangari");
		bookToSave.setIsbn("0231234-4566");
		bookToSave.setName("Spring-JPA");
		bookToSave.setPrice(25.0);

		ResponseEntity<Books> response = restTemplate.postForEntity("/books", bookToSave, Books.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		var body = response.getBody();
		assertEquals("Abhishek Rangari", body.getAuthor());
	}
}
