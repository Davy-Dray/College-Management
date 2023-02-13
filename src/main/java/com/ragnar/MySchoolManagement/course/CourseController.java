package com.ragnar.MySchoolManagement.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ragnar.MySchoolManagement.entenrollment.EnrollmentDto;

@RestController
@RequestMapping("api/v1/course")
public class CourseController {

	@Autowired
	CourseService courseService;

	@PostMapping
	public Course createCourse(@RequestBody RegisterCourse dto) {
		return courseService.createCourse(dto);
	}

	@GetMapping("/students/{courseId}")
	public List<EnrollmentDto> getStudentsInACourse(@PathVariable Long courseId) {
		return courseService.getAllStudentsInACourse(courseId);
	}

	@GetMapping("/teachers/{courseId}")
	public List<EnrollmentDto> getTeachersInACourse(@PathVariable Long courseId) {
		return courseService.getAllTeachersInACourse(courseId);
	}

	@GetMapping("/users/{courseId}")
	public List<EnrollmentDto> getAllUsersInACourse(@PathVariable Long courseId) {
		return courseService.getAllUsersInAcourse(courseId);
	}

	@GetMapping("/{courseId}")
	public CourseDto getCourseById(@PathVariable Long courseId) {
		return courseService.findCourseById(courseId);
	}

	@GetMapping("/user")
	public List<CourseDto> getCoursesForUser(Authentication authentication) {
		return courseService.getCoursesForUser(authentication.getName());
	}
}
