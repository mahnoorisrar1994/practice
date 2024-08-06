package com.student.services;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.student.model.Admission;
import com.student.repositories.AdmissionRepository;

@ExtendWith(MockitoExtension.class)
class AdmissionServiceTest {

	@Mock
	private AdmissionRepository admissionRepository;

	@InjectMocks
	private AdmissionServiceImpl admissionService;

	@Test
	void test_ReadAllExisting_admissions() {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");
		Admission secondAdmission = new Admission(2L, LocalDate.of(2021, 10, 2), "approved");
		// Configure the mock to return the student list
		when(admissionRepository.findAll()).thenReturn(asList(firstAdmission, secondAdmission));
		// Test the method
		assertThat(admissionService.readAllExistingAdmissions()).containsExactly(firstAdmission, secondAdmission);

	}

	@Test
	void test_getAdmissionById_found() {
		Admission firstAdmission = new Admission(1L, LocalDate.of(2021, 11, 2), "pending");
		when(admissionRepository.findById(1L)).thenReturn(Optional.of(firstAdmission));
		// Test the method
		assertThat(admissionService.findAdmissionById(1)).isSameAs(firstAdmission);
	}

	@Test
	void test_getAdmissionById_notFound() {
		when(admissionRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThat(admissionService.findAdmissionById(1)).isNull();
	}

	@Test
	void test_createNewAdmission_Detail() {
	}

	@Test
	void test_updateStudentInformation() {

	}

	@Test
	void test_deleteStudentDetail_found() {

	}

	@Test
	void test_deleteStudentDetail_notFound() {

	}

}
