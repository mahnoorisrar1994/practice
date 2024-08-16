package com.student.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/api/students/newStudent")
    public ResponseEntity<Student> newStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createNewStudentDetails(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.OK);  //student with status 200 OK
    }
    @PutMapping("/api/students/updateStudent/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudentInformation(id, student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }
    @DeleteMapping("/api/students/deleteStudent/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
