package com.rei.integrationtests.swagger;

import com.rei.integrationtests.mock.MockStudent;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.close();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() { RestAssured.baseURI = "http://localhost:" + port; }

    @Test
    public void shouldDisplaySwaggerPage() {
        var content = given()
                        .basePath("/swagger-ui/index.html")
                        .port(port)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        Assertions.assertTrue(content.contains("Swagger UI"));
    }
}
