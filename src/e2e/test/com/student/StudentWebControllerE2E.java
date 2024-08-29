package com.student;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

class StudentWebControllerE2E {

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
	void test_HomePage() {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("a[href*='/new"));
	}

	@Test
	void test_DeleteStudent() {
		driver.get(baseUrl + "/newAdmission");

		driver.findElement(By.name("admissionDate")).sendKeys("20-02-2024");
		driver.findElement(By.name("status")).sendKeys("Approved");
		driver.findElement(By.name("course")).sendKeys("Masters");
		driver.findElement(By.name("btn_submit")).click();

		driver.findElement(By.cssSelector("a[href*='/")).click();
		driver.findElement(By.cssSelector("a[href*='/new")).click();

		driver.findElement(By.name("firstName")).sendKeys("Hamza");
		driver.findElement(By.name("lastName")).sendKeys("Khan");
		driver.findElement(By.name("email")).sendKeys("hamzakhan@gmail.com");
		driver.findElement(By.name("btn_submit")).click();
		driver.findElement(By.cssSelector("a[href*='/delete/']")).click();

		driver.get(baseUrl);
		assertThat(driver.findElement(By.id("student_record")).getText()).doesNotContain("saved expense");

	}

}
