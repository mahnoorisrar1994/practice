package com.student;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdmissionRestControllerE2E {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@BeforeEach
	void setupTestData() {
		String newAdmissionJson = """
				{
				    "id": 2,
				    "admissionDate": "2024-01-01",
				    "status": "Pending",
				    "course": "Bachelors"
				}
				""";

		given().contentType(ContentType.JSON).body(newAdmissionJson).when().post("/api/admissions/newAdmission").then()
				.statusCode(200); 
	}

	@Test
	void test_GetAdmissionById() {
		long admissionId = 1;
		given().accept(ContentType.JSON).when().get("/api/admissions/{id}", admissionId).then().statusCode(200)
				.contentType(ContentType.JSON).body("id", equalTo((int) admissionId))
				.body("admissionDate", notNullValue()).body("status", notNullValue()).body("course", notNullValue());

	}

}
