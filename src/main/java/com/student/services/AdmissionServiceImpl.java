package com.student.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.student.model.Admission;
import com.student.repositories.AdmissionRepository;


@Service
public class AdmissionServiceImpl implements AdmissionService {
	
	private AdmissionRepository admissionRepository;

	public AdmissionServiceImpl(AdmissionRepository admissionRepository) {
		this.admissionRepository = admissionRepository;
	}

	
	public List<Admission> readAllExistingAdmissions() {
		return this.admissionRepository.findAll();
	}

	public Admission findAdmissionById(long id) {
		return admissionRepository.findById(id).orElse(null);
	}

	@Override
	public Admission createNewAdmissionDetails(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admission updateAdmissionInformation(long id, Admission replacement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAdmissionById(Long studentId) {
		// TODO Auto-generated method stub
		
	}

}
