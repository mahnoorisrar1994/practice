package com.student.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.student.model.Student;
import com.student.services.StudentService;

import java.util.List;

@RestController
public class StudentRestController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/api/allStudents")
	public List<Student> allStudents() {
	return studentService.readAllStudents();
	}
	
	@GetMapping("/api/students/{id}")
	public Student student(@PathVariable long id) {
	return studentService.findStudentById(id);
	}
}
