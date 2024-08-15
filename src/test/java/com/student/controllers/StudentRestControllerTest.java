package com.student.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.student.model.Admission;
import com.student.model.Student;
import com.student.services.StudentService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static java.util.Arrays.asList;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = StudentRestController.class)
class StudentRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StudentService studentService;

	@BeforeEach
	public void setUp() {
		// Initialize the MockMvc instance for RestAssuredMockMvc
		RestAssuredMockMvc.mockMvc(mvc);
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAllStudentsEmpty() throws Exception {
		this.mvc.perform(get("/api/allStudents").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]"));
		// the above checks that the content is an empty JSON list
	}

	@Test
	void testAllStudentsNotEmpty() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");
		Admission secondAdmission = new Admission(2L, LocalDate.of(2021, 10, 2), "approved");
		when(studentService.readAllStudents())
				.thenReturn(asList(new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission),
						new Student(2L, "Hamza", "Khan", "Hamzakhan@gmail.com", secondAdmission)));
		this.mvc.perform(get("/api/allStudents").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].firstName", is("Hamza")))
				.andExpect(jsonPath("$[0].lastName", is("Khan")))
				.andExpect(jsonPath("$[0].email", is("Hamzakhan@gmail.com"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].firstName", is("Hamza"))).andExpect(jsonPath("$[0].lastName", is("Khan")))
				.andExpect(jsonPath("$[1].email", is("Hamzakhan@gmail.com")));
	}

	@Test
	void test_OneStudentById_WithExistingStudent() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");
		when(studentService.findStudentById(anyLong()))
				.thenReturn(new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission));
		this.mvc.perform(get("/api/students/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.firstName", is("Hamza")))
				.andExpect(jsonPath("$.lastName", is("Khan")))
				.andExpect(jsonPath("$.email", is("Hamzakhan@gmail.com")));
	}

	@Test
	void test_OneStudentById_WithNotFoundStudent() throws Exception {
		when(studentService.findStudentById(anyLong())).thenReturn(null);
		this.mvc.perform(get("/api/students/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(""));
	}
}
