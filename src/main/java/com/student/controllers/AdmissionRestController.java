package com.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.model.Admission;
import com.student.services.AdmissionService;

@RestController
@RequestMapping("/api/admissions/")
public class AdmissionRestController {
	
	@Autowired
	private AdmissionService admissionService;
	
	@GetMapping("allAdmissions")
	public List<Admission> allAdmission() {
	return admissionService.readAllExistingAdmissions();
	}
	
	@GetMapping("{id}")
	public Admission admission(@PathVariable long id) {
	return admissionService.findAdmissionById(id);
	}

}
