package com.student.services;

import java.util.List;


import org.springframework.stereotype.Service;

import com.student.model.Student;
import com.student.repositories.StudentRepository;


@Service
public class StudentServiceImpl implements StudentService {
	
	private StudentRepository studentRepository;

	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}


	@Override
	public List<Student> fetchAllStudents() {
		return this.studentRepository.findAll();
	}

	@Override
	public Student findStudentById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student insertNewStudentDetails(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student updateStudentInformation(long id, Student replacement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteStudentById(Long studentId) {
		// TODO Auto-generated method stub
		
	}

}
