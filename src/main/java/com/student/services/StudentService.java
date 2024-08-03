package com.student.services;

import java.util.List;

import com.student.model.Student;


public interface StudentService {
	
    List<Student> fetchAllStudents();
	
	Student findStudentById(long id);
	
	Student insertNewStudentDetails(Student student);
	
    Student updateStudentInformation(long id, Student replacement);
	
	void deleteStudentById(Long studentId);

}
