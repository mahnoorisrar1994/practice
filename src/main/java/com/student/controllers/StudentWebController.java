package com.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable long id, Model model) {
		Student studentById = studentService.findStudentById(id);
		model.addAttribute("student", studentById);
		model.addAttribute("message", studentById == null ? "No student found with id: " + id : "");
		return "edit";
	}
}
