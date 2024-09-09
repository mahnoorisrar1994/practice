package com.student;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class StudentRestControllerE2E {

	private static final String BASE_URI = "http://localhost";
	private static final int PORT = 8080;
	private static final String STUDENT_ENDPOINT = "/api/students";

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = BASE_URI;
		RestAssured.port = PORT;
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

	    createStudent(newStudentJson)
	        .then()
	        .statusCode(200)
	        .contentType(ContentType.JSON)
	        .body("id", notNullValue())  // Assuming the ID is returned in the response
	        .body("firstName", equalTo("Hamza"))
	        .body("lastName", equalTo("Khan"))
	        .body("email", equalTo("Hamzakhan@gmail.com"))
	        .body("admission.admissionDate", equalTo("2024-02-20"))
	        .body("admission.status", equalTo("Approved"))
	        .body("admission.course", equalTo("Masters"));
	}

	private io.restassured.response.Response createStudent(String studentJson) {
	    return given()
	        .contentType(ContentType.JSON)
	        .body(studentJson)
	        .when()
	        .post(STUDENT_ENDPOINT + "/newStudent");
	}


}
