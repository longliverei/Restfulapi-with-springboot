package com.rei.integrationtests.controller;

import com.rei.integrationtests.mock.MockStudent;
import com.rei.models.dto.StudentDto;
import com.rei.services.StudentsService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentsControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    StudentsService service;

    MockStudent mock;

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
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        mock = new MockStudent();
    }

    @Test
    @Order(1)
    void shouldCreateStudent() {
        StudentDto dto = mock.mockStudentDto();

        ValidatableResponse validatableResponse = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/api/students/v1")
                .then()
                .log().all().assertThat().statusCode(201)
                .and()
                .header("Location", equalTo(baseURI + "/api/students/v1/" + dto.getId()));
    }

    @Test
    @Order(2)
    void shouldGetAllStudents() {
        ValidatableResponse validatableResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/students/v1")
                .then()
                .log().all().assertThat().statusCode(200);
    }
    @Test
    @Order(3)
    void shouldUpdateStudent() {
        StudentDto dto = mock.mockStudentDto();
        dto.setFirstName("Reinaldo");

        ValidatableResponse validatableResponse = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put("/api/students/v1")
                .then()
                .log().all().assertThat().statusCode(204);
    }

    @Test
    @Order(4)
    void shouldGetAStudent() {
        ValidatableResponse validatableResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/students/v1/1")
                .then()
                .log().all().assertThat().statusCode(200)
                .and()
                .body("id", equalTo(1));
    }

    @Test
    @Order(5)
    void shouldDeleteAStudent() {
        ValidatableResponse validatableResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/students/v1/1")
                .then()
                .log().all().assertThat().statusCode(204);
    }

}
