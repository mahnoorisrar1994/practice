package com.student.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.student.model.Student;
import com.student.services.StudentService;

@Controller
public class StudentWebController {

	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String INDEX = "index";
	private static final String EDIT = "edit";

	@Autowired
	private StudentService studentService;

	@GetMapping("/")
	public String index(Model model) {
		List<Student> allStudents = studentService.readAllStudents();
		model.addAttribute("students", allStudents);
		model.addAttribute(MESSAGE_ATTRIBUTE, allStudents.isEmpty() ? "No student detail present" : "");
		return INDEX;

	}

	@GetMapping("/edit/{id}")
	public String editStudent(@PathVariable long id, Model model) {
		Student studentById = studentService.findStudentById(id);
		model.addAttribute("student", studentById);
		model.addAttribute(MESSAGE_ATTRIBUTE, studentById == null ? "No student found with id: " + id : "");
		return EDIT;
	}

	@GetMapping("/new")
	public String newStudent(Model model) {
		model.addAttribute("student", new Student());
		model.addAttribute(MESSAGE_ATTRIBUTE, "");
		return EDIT;

	}

	@PostMapping("/save")
	public String saveStudent(Student student) {
		final Long id = student.getId();
		if (id == null) {
			studentService.createNewStudentDetails(student);
		} else {
			studentService.updateStudentInformation(id, student);
		}
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteProperty(@PathVariable Long id, Model model) {
		studentService.deleteStudentById(id);
		return "redirect:/";
	}
}
