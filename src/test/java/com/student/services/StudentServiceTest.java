package com.student.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	void test_ReadAllStudents() {
		Student firstStudent = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com");
		Student secondStudent = new Student(2L, "Hamza", "Khan", "Hamzakhan@gmail.com");
		// Configure the mock to return the student list
		when(studentRepository.findAll()).thenReturn(asList(firstStudent, secondStudent));
		// Test the method
		assertThat(studentService.readAllStudents()).containsExactly(firstStudent, secondStudent);

	}

	@Test
	void test_getStudentById_found() {
		Student firstStudent = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com");
		when(studentRepository.findById(1L)).thenReturn(Optional.of(firstStudent));
		// Test the method
		assertThat(studentService.findStudentById(1)).isSameAs(firstStudent);
	}

	@Test
	void test_getStudentById_notFound() {
		when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThat(studentService.findStudentById(1)).isNull();
	}

	@Test
	void test_createNewStudent_Details() {
		Student toSave = spy(new Student(99L, "", "", ""));
		Student saved = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com");

		when(studentRepository.save(any(Student.class))).thenReturn(saved);

		Student result = studentService.createNewStudentDetails(toSave);

		assertThat(result).isSameAs(saved);

		InOrder inOrder = Mockito.inOrder(toSave, studentRepository);
		inOrder.verify(toSave).setId(null);
		inOrder.verify(studentRepository).save(toSave);
	}

	@Test
	void test_updateStudent_Information() {
		Student replacement = spy(new Student(null, "Hamza", "Khan", "Hamza@gmail.com"));
		Student replaced = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com");

		when(studentRepository.save(any(Student.class))).thenReturn(replaced);

		Student result = studentService.updateStudentInformation(1L, replacement);

		assertThat(result).isSameAs(replaced);

		InOrder inOrder = Mockito.inOrder(replacement, studentRepository);
		inOrder.verify(replacement).setId(1L);
		inOrder.verify(studentRepository).save(replacement);
	}

	@Test
	void test_deleteStudentDetail_found() {
		Student existingStudentDetails = new Student(1L, "Hamza", "Khan", "Hamzakhan@gmail.com");
		
		when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudentDetails));
		
		studentService.deleteStudentById(1L);
		
		verify(studentRepository, times(1)).delete(existingStudentDetails);
	}
	
	@Test
	void test_deleteStudentDetail_notFound() {
		Long studentId = 1L;
		when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

		Throwable exception = assertThrows(NoSuchElementException.class, () -> {
			studentService.deleteStudentById(studentId);
		});
		assertThat(exception.getMessage()).isEqualTo("Student does not exist");
		verify(studentRepository, never()).delete(any(Student.class));
	}
}
