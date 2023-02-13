package com.ragnar.MySchoolManagement.user.teacher;


import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
	
	private final TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
//  	
//	@GetMapping("/teachers")
//	public List<Teacher> getAllTeachers() {
//		return teacherService.
//	}
//	
}
