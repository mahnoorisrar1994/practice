package com.student.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.model.Student;
import com.student.repositories.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	
	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentServiceImpl studentService;
	
	@Test
	void test_fetchAllStudents() {
		Student firstStudent = new Student(1L, "Hamza","Khan","Hamzakhan@gmail.com");
		Student secondStudent = new Student(2L, "Hamza","Khan","Hamzakhan@gmail.com");
		// Configure the mock to return the student list 
		when(studentRepository.findAll()).thenReturn(asList(firstStudent, secondStudent));
		// Test the method
		assertThat(studentService.fetchAllStudents()).containsExactly(firstStudent, secondStudent);

	}
	

}
