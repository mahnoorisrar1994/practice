package com.student;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class StudentRestControllerE2E {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}

	@BeforeEach
	void setupTestData() {
		String newAdmissionJson = """
				{
				    "admissionDate": "2024-02-20",
				    "status": "Approved",
				    "course": "Masters"
				}
				""";

		given().contentType(ContentType.JSON).body(newAdmissionJson).when().post("/api/admissions/newAdmission").then()
				.statusCode(200);
	}

	@Test
	void test_CreateStudent() {
		String newStudentJson = """
				{
				    "firstName": "Hamza",
				    "lastName": "Khan",
				    "email": "Hamzakhan@gmail.com",
				    "admission": {
				        "admissionDate": "2024-02-20",
				        "status": "Approved",
				        "course": "Masters"
				    }
				}
				""";

		given().contentType(ContentType.JSON).body(newStudentJson).when().post("/api/students/newStudent").then()
				.statusCode(200).contentType(ContentType.JSON).body("firstName", equalTo("Hamza"))
				.body("lastName", equalTo("Khan")).body("email", equalTo("Hamzakhan@gmail.com"))
				.body("admission.admissionDate", equalTo("2024-02-20")).body("admission.status", equalTo("Approved"))
				.body("admission.course", equalTo("Masters"));

		long studentId = 1;
		given().accept(ContentType.JSON).when().get("/api/students/{id}", studentId).then().statusCode(200)
				.body("firstName", equalTo("Hamza")).body("lastName", equalTo("Khan"))
				.body("email", equalTo("Hamzakhan@gmail.com")).body("admission.admissionDate", equalTo("2024-02-20"))
				.body("admission.status", equalTo("Approved")).body("admission.course", equalTo("Masters"));
	}



}
