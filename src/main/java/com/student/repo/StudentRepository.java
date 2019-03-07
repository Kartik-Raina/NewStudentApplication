package com.student.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.student.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
	
	@Query("SELECT u FROM Student u WHERE u.roleNumber = ?1")
	Student findStudentByRoleNumber(Long roleNumber);

}
