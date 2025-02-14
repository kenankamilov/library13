package com.example.library.management;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
public class PostgreSQLTest {

    @Autowired
    private PostgreSQLContainer<?> postgresContainer;

    @Test
    void testPostgresConnection() {
        System.out.println("PostgreSQL Container URL: " + postgresContainer.getJdbcUrl());
        // Burada testlər edə bilərik
    }
}
