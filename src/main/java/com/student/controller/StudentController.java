package com.student.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.student.exception.StudentNotFoundException;
import com.student.model.Student;
import com.student.repo.StudentRepository;

@RestController
public class StudentController {

	@Autowired
	StudentRepository studentRepository;

	@GetMapping("/students")
	public List<Student> retrieveAllStudents() {
		return (List<Student>) studentRepository.findAll();
	}

	@GetMapping("/students/{id}")
	public Student retrieveStudent(@PathVariable long id) {
		Optional<Student> student = studentRepository.findById(id);

		if (!student.isPresent())
			throw new StudentNotFoundException("id-" + id);

		return student.get();
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable long id) {
		studentRepository.deleteById(id);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@RequestBody Student student) {
		Student savedStudent = studentRepository.save(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/students/{roleNumber}")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Long roleNumber) {

		Student std = studentRepository.findStudentByRoleNumber(roleNumber);

		if (Objects.isNull(std))
			// return ResponseEntity.notFound().build();
			throw new StudentNotFoundException("Role_Number-" + roleNumber);

		// student.setId(std.getId());
		std.setFirstName(student.getFirstName());
		std.setLastName(student.getLastName());
		std.setRoleNumber(student.getRoleNumber());

		studentRepository.save(std);

		return ResponseEntity.ok().build();
	}

}