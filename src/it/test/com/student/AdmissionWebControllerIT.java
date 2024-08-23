package com.student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.student.model.Admission;
import com.student.model.Student;
import com.student.repositories.AdmissionRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdmissionWebControllerIT {
	
	@Autowired
	private AdmissionRepository admissionRepository;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseUrl;

	@BeforeEach
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new HtmlUnitDriver();
		// always start with an empty database
		admissionRepository.deleteAll();
		admissionRepository.flush();
	}

	@AfterEach
	public void teardown() {
		driver.quit();
	}

	@Test
	void test_HomePage() {
		Admission admission = admissionRepository
				.save(new Admission(null, LocalDate.of(2021, 02, 2), "pending", "bachelors"));
		driver.get(baseUrl);
		// the table shows the test student
		assertThat(driver.findElement(By.id("admission_record")).getText()).contains("2021-02-02", "pending", "bachelors", "Edit", "Delete");

		driver.findElement(By.cssSelector("a[href*='/editAdmission/" + admission.getId() + "']"));
	}
	@Test
	void test_DeleteAdmission() throws Exception {
		Admission admission = admissionRepository
				.save(new Admission(null, LocalDate.of(2021, 02, 2), "pending", "bachelors"));

		driver.get(baseUrl + "/deleteAdmission/" + admission.getId());

		assertFalse(admissionRepository.findById(admission.getId()).isPresent(),
				"Admission deleted");

	}
	@Test
	void test_ShowNewAdmissionForm() {
	    driver.get(baseUrl + "/newAdmission");
	    driver.findElement(By.name("admissionDate")).sendKeys("2021-02-02");
	    driver.findElement(By.name("status")).sendKeys("pending");
	    driver.findElement(By.name("course")).sendKeys("bachelors");

	    driver.findElement(By.name("btn_submit")).click();

	    String currentUrl = driver.getCurrentUrl();
	    assertThat(currentUrl).isEqualTo(baseUrl + "/saveAdmission"); 
	    
	}

}
