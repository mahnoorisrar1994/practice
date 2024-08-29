package com.student;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;


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

		// Set the implicit wait time to 10 seconds
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@AfterAll
	public static void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	  @Test
	    void test_CreateAdmission() {
	        // Navigate to the new admission page
	        driver.get(baseUrl + "/newAdmission");

	        // Fill out the form
	        driver.findElement(By.name("admissionDate")).sendKeys("29-08-2024");
	        driver.findElement(By.name("status")).sendKeys("Approved");
	        driver.findElement(By.name("course")).sendKeys("Masters");
	        driver.findElement(By.name("btn_submit")).click();

	        // Assert that the admission record contains the expected values
	        assertThat(driver.findElement(By.id("admission_record")).getText())
	            .contains("2024-08-29", "Approved", "Masters");
	    }

}
