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

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
		 // Given
		Admission toSave = spy(new Admission());
		Admission saved = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");

		when(admissionRepository.save(any(Admission.class))).thenReturn(saved);
		// When
		
		Admission result = admissionService.createNewAdmissionDetails(toSave);
		// Then
		assertThat(result).isSameAs(saved);

		InOrder inOrder = Mockito.inOrder(toSave, admissionRepository);
		inOrder.verify(toSave).setId(null);
		inOrder.verify(admissionRepository).save(toSave);
	}

	@Test
	void test_updateAdmissionInformation() {
		Admission replacement = spy(new Admission(null, LocalDate.of(2021, 02, 2), "pending"));
		Admission replaced = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");

		when(admissionRepository.save(any(Admission.class))).thenReturn(replaced);

		Admission result = admissionService.updateAdmissionInformation(1L, replacement);

		assertThat(result).isSameAs(replaced);

		InOrder inOrder = Mockito.inOrder(replacement, admissionRepository);
		inOrder.verify(replacement).setId(1L);
		inOrder.verify(admissionRepository).save(replacement);

	}

	@Test
	void test_deleteAdmissiontDetail_found() {
        Admission existingAdmissionDetails = new Admission(1L, LocalDate.of(2021, 02, 2), "pending");
		
		when(admissionRepository.findById(1L)).thenReturn(Optional.of(existingAdmissionDetails));
		
		admissionService.deleteAdmissionById(1L);
		
		verify(admissionRepository, times(1)).delete(existingAdmissionDetails);

	}

	@Test
	void test_deleteAdmissionDetail_notFound() {
		Long admissionId = 1L;
		when(admissionRepository.findById(admissionId)).thenReturn(Optional.empty());

		Throwable exception = assertThrows(NoSuchElementException.class, () -> {
			admissionService.deleteAdmissionById(admissionId);
		});
		assertThat(exception.getMessage()).isEqualTo("Admission does not exist");
		verify(admissionRepository, never()).delete(any(Admission.class));

	}

}
