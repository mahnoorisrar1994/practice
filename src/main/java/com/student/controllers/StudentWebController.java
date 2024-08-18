package com.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.student.model.Student;
import com.student.services.StudentService;

@Controller
public class StudentWebController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/")
	public String index(Model model) {
		List<Student> allStudents = studentService.readAllStudents();
		model.addAttribute("students", allStudents);
		model.addAttribute("message", allStudents.isEmpty() ? "No student detail present" : "");
		return "index";

	}
}
