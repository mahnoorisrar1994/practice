package com.student;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AdmissionWebControllerE2E {
	
	private static int port = Integer.parseInt(System.getProperty("server.port", "8080"));
	private static String baseUrl = "http://localhost:" + port;
	private static WebDriver driver;

	@BeforeAll
	public static void setupClass() {
		// setup Chrome Driver
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	void test_CreateAdmission() {
		// Create a new Admission
		driver.get(baseUrl + "/newAdmission");
		driver.findElement(By.name("admissionDate")).sendKeys("29-08-2024");
		driver.findElement(By.name("status")).sendKeys("Approved");
		driver.findElement(By.name("course")).sendKeys("Masters");
		driver.findElement(By.name("btn_submit")).click();

		assertThat(driver.findElement(By.id("admission_record")).getText()).
		contains("2024-08-29","Approved", "Masters");

	
	}
	

}
