package com.ragnar.MySchoolManagement.user.student;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teacher")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public Page<Student> getAllStudents() {
		return studentService.findAllStudent();
	}

	@GetMapping("/{id}")
	public StudentDTO getStudentById(@PathVariable Long id) {
		return studentService.findByStudentIds(id);
	}

}
