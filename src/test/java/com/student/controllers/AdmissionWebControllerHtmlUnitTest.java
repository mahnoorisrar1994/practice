package com.student.controllers;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.student.model.Admission;
import com.student.services.AdmissionService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AdmissionWebController.class)
class AdmissionWebControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;

	@MockBean
	private AdmissionService admissionService;

	@Test
	void test_HomePageTitle() throws Exception {
		HtmlPage page = webClient.getPage("/admissions");
		assertThat(page.getTitleText()).isEqualTo("Admissions");
	}

	@Test
	void test_HomePageWithNoAdmission() throws Exception {
		when(admissionService.readAllExistingAdmissions()).thenReturn(emptyList());
		HtmlPage page = this.webClient.getPage("/admissions");
		assertThat(page.getBody().getTextContent()).contains("No admission found");
	}

	@Test
	void test_DeleteAdmission_ShouldDisplayConfirmationMessage() throws Exception {
		doNothing().when(admissionService).deleteAdmissionById(1L);

		HtmlPage page = webClient.getPage("/deleteAdmission/1");

		verify(admissionService, times(1)).deleteAdmissionById(1L);

		String pageContent = page.getBody().getTextContent();
		assertThat(pageContent).contains("Admission with ID 1 was deleted.");

		HtmlAnchor newStudentLink = page.getAnchorByHref("/newAdmission");
		assertThat(newStudentLink).isNotNull();
	}
	
	@Test
	void test_HomePageWithAdmissions_ShouldShowThemInATable() throws Exception {
		when(admissionService.readAllExistingAdmissions())
				.thenReturn(asList(new Admission(1L, LocalDate.of(2021, 2, 2), "pending","bachelors"),
						new Admission(2L, LocalDate.of(2021, 4, 2), "approved", "masters")));

		HtmlPage page = this.webClient.getPage("/admissions");
		assertThat(page.getBody().getTextContent()).doesNotContain("No admission found");
		page.getAnchorByHref("/editAdmission/1");
		page.getAnchorByHref("/editAdmission/2");

	}
	@Test
	void test_EditNonExistentAdmission() throws Exception {
		when(admissionService.findAdmissionById(1L)).thenReturn(null);

		HtmlPage page = this.webClient.getPage("/editAdmission/1");

		assertThat(page.getBody().getTextContent()).contains("No admission found with id: 1");

	}

	@Test
	void test_EditExistentAdmission() throws Exception {
	    Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 2, 2), "pending", "bachelors");
	    when(admissionService.findAdmissionById(1)).thenReturn(firstAdmission);

	    HtmlPage page = this.webClient.getPage("/editAdmission/1");

	    final HtmlForm form = page.getFormByName("admission_record");

	    form.getInputByName("admissionDate").setValueAttribute("2021-02-02");
	    form.getInputByName("status").setValueAttribute("pending");

	    // Interact with the select element for the course
	    HtmlSelect courseSelect = form.getSelectByName("course");
	    courseSelect.setSelectedAttribute("Bachelors", true);

	    HtmlButton submitButton = (HtmlButton) form.getButtonByName("btn_submit");
	    HtmlPage resultPage = submitButton.click();

	    assertThat(resultPage.getUrl().toString()).endsWith("/admissions");

	    verify(admissionService).updateAdmissionInformation(eq(1L), any(Admission.class));
	}


	

}
