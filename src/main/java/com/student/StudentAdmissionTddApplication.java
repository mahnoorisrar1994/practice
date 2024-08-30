package com.student;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentAdmissionTddApplication {

	public static void main(String[] args) {
		// Set the default Locale and TimeZone
		Locale.setDefault(Locale.US);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// Start the Spring Boot application
		SpringApplication.run(StudentAdmissionTddApplication.class, args);
	}

}
