package com.student.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.student.model.Admission;
import com.student.model.Student;
import com.student.services.StudentService;
import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StudentRestController.class)
public class StudentRestControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StudentService studentService;

	@Test
	public void testAllStudentsEmpty() throws Exception {
		this.mvc.perform(get("/api/allStudents").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]"));
		// the above checks that the content is an empty JSON list
	}

	@Test
	public void testAllStudentsNotEmpty() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");
		Admission secondAdmission = new Admission(2L, LocalDate.of(2021, 10, 2), "approved");
		when(studentService.readAllStudents())
				.thenReturn(asList(new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission), new Student(2L, "Hamza", "Khan", "Hamzakhan@gmail.com", secondAdmission)));
		this.mvc.perform(get("/api/allStudents").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].firstName", is("Hamza")))
		.andExpect(jsonPath("$[0].lastName", is("Khan"))).andExpect(jsonPath("$[0].email", is("Hamzakhan@gmail.com")))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[1].firstName", is("Hamza"))).andExpect(jsonPath("$[0].lastName", is("Khan")))
		.andExpect(jsonPath("$[1].email", is("Hamzakhan@gmail.com")));
}

}
