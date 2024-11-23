package com.example.demo.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Book(@NotNull String name,

		@NotNull @Size(max = 10) String isbn,

		@NotNull String author,

		@Positive Double price) {
}