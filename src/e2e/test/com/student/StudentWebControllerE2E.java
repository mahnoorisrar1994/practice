package com.student;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

class StudentWebControllerE2E {
	
	

	
	@Test
    void testChromeDriverSetup() {
  
        WebDriverManager.chromedriver().setup();
        
        WebDriver driver = new ChromeDriver();
   
        driver.get("https://www.google.com");
        

        System.out.println("Page title is: " + driver.getTitle());
 
        driver.quit();
    }





}
