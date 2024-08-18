package com.student.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.student.services.StudentService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = StudentWebController.class)
class StudentWebControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;
	
	@MockBean
	private StudentService studentsService;

	@Test
	void testHomePageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/");
		assertThat(page.getTitleText()).isEqualTo("Students");
	}

}
