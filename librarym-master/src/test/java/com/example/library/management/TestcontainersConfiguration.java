package com.example.library.management;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
@Configuration
public class TestcontainersConfiguration {

	private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("library")
			.withUsername("postgres")
			.withPassword("12345");

	@PostConstruct
	public void startContainer() {
		POSTGRES_CONTAINER.start();
	}

	@Bean
	public PostgreSQLContainer<?> postgresContainer() {
		return POSTGRES_CONTAINER;
}
}