package com.student.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import static java.util.Collections.emptyList;
import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.student.model.Admission;
import com.student.model.Student;
import com.student.services.AdmissionService;
import com.student.services.StudentService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = StudentWebController.class)
class StudentWebControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;

	@MockBean
	private StudentService studentService;
	
	@MockBean
	private AdmissionService admissionService;

	@Test
	void test_HomePageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/");
		assertThat(page.getTitleText()).isEqualTo("Students");
	}

	@Test
	void test_HomePageWithNoStudents() throws Exception {
		when(studentService.readAllStudents()).thenReturn(emptyList());
		HtmlPage page = this.webClient.getPage("/");
		assertThat(page.getBody().getTextContent()).contains("No student found");
	}

	@Test
	void test_HomePageWithStudents_ShouldShowThemInATable() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 2, 2), "pending");
		Admission secondAdmission = new Admission(1L, LocalDate.of(2021, 4, 2), "approved");
		when(studentService.readAllStudents())
				.thenReturn(asList(new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission),
						new Student(2L, "Hamza", "Khan", "Hamzakhan@gmail.com", secondAdmission)));

		HtmlPage page = this.webClient.getPage("/");
		assertThat(page.getBody().getTextContent()).doesNotContain("No student found");
		page.getAnchorByHref("/edit/1");
		page.getAnchorByHref("/edit/2");

	}

	@Test
	void test_EditNonExistentStudent() throws Exception {
		when(studentService.findStudentById(1L)).thenReturn(null);

		HtmlPage page = this.webClient.getPage("/edit/1");

		assertThat(page.getBody().getTextContent()).contains("No student found with id: 1");

	}

	@Test
	void test_EditExistentStudent() throws Exception {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 2, 2), "pending");
		Student existingStudent = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com", firstAdmission);
		when(studentService.findStudentById(1)).thenReturn(existingStudent);

		HtmlPage page = this.webClient.getPage("/edit/1");

		final HtmlForm form = page.getFormByName("student_record");

		form.getInputByName("firstName").setValueAttribute("modified name");
		form.getInputByName("lastName").setValueAttribute("200");
		form.getInputByName("email").setValueAttribute("modified description");

		form.getSelectByName("admission").setSelectedAttribute("1", true);

		HtmlButton submitButton = (HtmlButton) form.getButtonByName("btn_submit");
		HtmlPage resultPage = submitButton.click();

		assertThat(resultPage.getUrl().toString()).endsWith("/");

		verify(studentService).updateStudentInformation(eq(1L), any(Student.class));
	}
}
