package com.student.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.model.Admission;
import com.student.services.AdmissionService;

@RestController
@RequestMapping("/api/admissions/")
public class AdmissionRestController {

	private final AdmissionService admissionService;

	public AdmissionRestController(AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	@GetMapping("allAdmissions")
	public List<Admission> allAdmission() {
		return admissionService.readAllExistingAdmissions();
	}

	@GetMapping("{id}")
	public Admission admission(@PathVariable long id) {
		return admissionService.findAdmissionById(id);
	}

	@PostMapping("newAdmission")
	public ResponseEntity<Admission> newAdmission(@RequestBody Admission admission) {
		Admission createdAdmission = admissionService.createNewAdmissionDetails(admission);
		return new ResponseEntity<>(createdAdmission, HttpStatus.OK);
	}

	@DeleteMapping("deleteAdmission/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		admissionService.deleteAdmissionById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("updateAdmission/{id}")
	public ResponseEntity<Admission> updateAdmission(@PathVariable Long id, @RequestBody Admission admission) {
		Admission updatedAdmission = admissionService.updateAdmissionInformation(id, admission);
		return new ResponseEntity<>(updatedAdmission, HttpStatus.OK);
	}

}
