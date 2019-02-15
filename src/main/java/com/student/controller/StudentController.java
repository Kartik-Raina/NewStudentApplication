package com.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.model.Student;
import com.student.repo.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	StudentRepository studentRepo;

	@GetMapping("/students")
	public List<Student> getStudents() {
		return (List<Student>) studentRepo.findAll();
	}
	
	
}