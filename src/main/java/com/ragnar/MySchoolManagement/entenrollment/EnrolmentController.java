package com.ragnar.MySchoolManagement.entenrollment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/enrolment")
public class EnrolmentController {

	private final EnrolmentService enrolmentService;

	public EnrolmentController(EnrolmentService enrolmentService) {
		this.enrolmentService = enrolmentService;
	}

	@DeleteMapping("/{userId}/{courseId}")
	public ResponseEntity<Void> unEnrolFromCourse(@PathVariable Long userId, @PathVariable Long courseId) {
		boolean result = enrolmentService.unEnrolFromCourse(courseId, userId);
		if (result) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{courseId}")
	public ResponseEntity<?> enrollToCourse(Authentication authentication, @PathVariable Long courseId) {
		enrolmentService.enrolToCourse(authentication.getName(), courseId);
		return ResponseEntity.ok().build();
	}

} 
