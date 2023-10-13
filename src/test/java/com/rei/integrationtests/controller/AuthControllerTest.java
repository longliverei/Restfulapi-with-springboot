package com.rei.integrationtests.controller;

import com.rei.models.dto.security.AccountCredentialsDto;
import com.rei.models.dto.security.TokenDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private Integer port;

    private static TokenDto tokenDto;

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
    @Order(1)
    void shouldSignIn() {
        AccountCredentialsDto user = new AccountCredentialsDto("admin", "admin");

        tokenDto = given()
                .basePath("/auth/signin")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(TokenDto.class);

        Assertions.assertNotNull(tokenDto.getAccessToken());
        Assertions.assertNotNull(tokenDto.getRefreshToken());
    }

    @Test
    @Order(2)
    void shouldRefreshToken() {

        var newTokenDto = given()
                .basePath("/auth/refresh")
                .contentType(ContentType.JSON)
                .pathParam("username", tokenDto.getUsername())
                .header("Authorization", "Bearer " + tokenDto.getRefreshToken())
                .when()
                .put("/{username}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenDto.class);

        assertNotNull(newTokenDto.getAccessToken());
        assertNotNull(newTokenDto.getRefreshToken());
    }
}
