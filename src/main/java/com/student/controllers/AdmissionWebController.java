package com.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.student.model.Admission;
import com.student.services.AdmissionService;


@Controller
public class AdmissionWebController {
	
	@Autowired
	private AdmissionService admissionService;

	@GetMapping("/admissions")
	public String index(Model model) {
		List<Admission> admissions = admissionService.readAllExistingAdmissions();
		model.addAttribute("admissions", admissions);
		model.addAttribute("message", admissions.isEmpty() ? "No admission is presented" : "");
		return "admission_index";
	}

}
