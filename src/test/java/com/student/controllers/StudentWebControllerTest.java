package com.student.controllers;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;

import com.student.model.Admission;
import com.student.model.Student;
import com.student.services.StudentService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = StudentWebController.class)
class StudentWebControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StudentService studentService;

	@Test
	void test_Status200() throws Exception {
		mvc.perform(get("/")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void test_ReturnHomeView() throws Exception {
		ModelAndViewAssert.assertViewName(mvc.perform(get("/")).andReturn().getModelAndView(), "index");

	}

	@Test
	void test_HomeView_ShowStudents() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 2, 2), "pending");
		Student student = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission);
		List<Student> students = Arrays.asList(student);

		when(studentService.readAllStudents()).thenReturn(students);

		mvc.perform(get("/")).andExpect(view().name("index")).andExpect(model().attribute("students", students))
				.andExpect(model().attribute("message", ""));
	}

	@Test
	void test_HomeView_ShowsMessageWhenThereAreNoStudents() throws Exception {
		when(studentService.readAllStudents()).thenReturn(Collections.emptyList());
		mvc.perform(get("/")).andExpect(view().name("index"))
				.andExpect(model().attribute("students", Collections.emptyList()))
				.andExpect(model().attribute("message", "No student detail present"));
	}

	@Test
	void test_EditStudent_WhenStudentIsFound() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 2, 2), "pending");
		Student students = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission);
		when(studentService.findStudentById(1L)).thenReturn(students);
		mvc.perform(get("/edit/1")).andExpect(view().name("edit")).andExpect(model().attribute("student", students))
				.andExpect(model().attribute("message", ""));
	}

	@Test
	void test_EditStudent_WhenStudentIsNotFound() throws Exception {
		when(studentService.findStudentById(1L)).thenReturn(null);
		mvc.perform(get("/edit/1")).andExpect(view().name("edit")).andExpect(model().attribute("students", nullValue()))
				.andExpect(model().attribute("message", "No student found with id: 1"));

	}

	@Test
	void test_EditNewStudent() throws Exception {
		mvc.perform(get("/new")).andExpect(view().name("edit")).andExpect(model().attribute("student", new Student()))
				.andExpect(model().attribute("message", ""));
		verifyNoInteractions(studentService);
	}

	@Test
	void test_PostStudentWithoutId_ShouldInsertNewStudent() throws Exception {
		mvc.perform(post("/save").param("firstName", "Hamza").param("lastName", "Khan").param("email",
				"Hamzakhan@gmail.com")).andExpect(redirectedUrl("/")); // go back to the main page

		verify(studentService).createNewStudentDetails(ArgumentMatchers.any(Student.class));
	}

	@Test
	void test_PostStudentWithId_ShouldUpdateExistingStudent() throws Exception {
		mvc.perform(post("/save").param("id", "1").param("firstName", "Hamza").param("lastName", "Khan").param("email",
				"Hamzakhan@gmail.com")).andExpect(view().name("redirect:/")); // go back to the main page

		verify(studentService).updateStudentInformation(eq(1L), ArgumentMatchers.any(Student.class));
	}

	@Test
	void test_DeleteStudent_ByExistingIdShouldDelete() throws Exception {
		mvc.perform(get("/delete/1")).andExpect(view().name("redirect:/"));
		verify(studentService, times(1)).deleteStudentById(1L);
	}

}
