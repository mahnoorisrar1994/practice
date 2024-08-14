package com.student.services;

import java.util.List;

import com.student.model.Admission;

public interface AdmissionService {

	List<Admission> readAllExistingAdmissions();

	Admission findAdmissionById(long id);

	Admission createNewAdmissionDetails(Admission admission);

	Admission updateAdmissionInformation(long id, Admission replacement);

	void deleteAdmissionById(Long admissionId);

}
