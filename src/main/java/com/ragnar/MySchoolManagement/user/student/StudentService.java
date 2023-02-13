package com.ragnar.MySchoolManagement.user.student;

import java.util.List;

import org.springframework.data.domain.Page;



public interface StudentService {

	Page<Student> findAllStudent();

	Student findByStudentId(Long id);

	void deleteStudent(Long id);


	int getStudentCourseCount(String emai );

	StudentDTO findByStudentIds(Long studentId);

	
}
