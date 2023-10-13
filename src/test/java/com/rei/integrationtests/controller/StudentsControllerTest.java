package com.rei.integrationtests.controller;

import com.rei.integrationtests.mock.MockStudent;
import com.rei.models.dto.StudentDto;
import com.rei.models.dto.security.AccountCredentialsDto;
import com.rei.models.dto.security.TokenDto;
import com.rei.services.StudentsService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
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

    private static RequestSpecification specification;

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
    public void shouldAuthenticate() {
        AccountCredentialsDto user = new AccountCredentialsDto("admin", "admin");

        var accessToken = given()
                .basePath("/auth/signin")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post()
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(TokenDto.class).getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .setBasePath("/api/students/v1")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(2)
    void shouldCreateStudent() {
        StudentDto dto = mock.mockStudentDto();

        ValidatableResponse validatableResponse = given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post()
                .then()
                .assertThat().statusCode(201)
                .and()
                .header("Location", equalTo(baseURI + "/api/students/v1/" + dto.getId()));
    }

    @Test
    @Order(3)
    void shouldGetAllStudents() {
        ValidatableResponse validatableResponse = given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .log().all().assertThat().statusCode(200);
    }
    @Test
    @Order(4)
    void shouldUpdateStudent() {
        StudentDto dto = mock.mockStudentDto();
        dto.setFirstName("Reinaldo");

        ValidatableResponse validatableResponse = given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put()
                .then()
                .log().all().assertThat().statusCode(204);
    }

    @Test
    @Order(5)
    void shouldDisableStudent() {
        ValidatableResponse validatableResponse = given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .when()
                .patch("1")
                .then()
                .log().all().assertThat().statusCode(204);
    }

    @Test
    @Order(6)
    void shouldGetAStudent() {
        ValidatableResponse validatableResponse = given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .when()
                .get("1")
                .then()
                .log().all().assertThat().statusCode(200)
                .and()
                .body("id", equalTo(1));
    }

    @Test
    @Order(7)
    void shouldDeleteAStudent() {
        ValidatableResponse validatableResponse = given()
                .spec(specification)
                .contentType(ContentType.JSON)
                .when()
                .delete("1")
                .then()
                .log().all().assertThat().statusCode(204);
    }

}
