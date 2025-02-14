package com.example.library.management;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgreSQLContainerTest {

    public static PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpassword");

    @Test
    public void testDatabaseConnection() {
        try (PostgreSQLContainer<?> ignored = postgresContainer) {
            // PostgreSQLContainer'ə qoşulma testlərini buraya əlavə edə bilərsiniz
            System.out.println("PostgreSQL Container URL: " + postgresContainer.getJdbcUrl());
        }
    }
}
