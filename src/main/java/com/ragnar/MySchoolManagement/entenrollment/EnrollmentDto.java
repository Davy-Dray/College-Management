package com.ragnar.MySchoolManagement.entenrollment;

import java.time.LocalDateTime;

import com.ragnar.MySchoolManagement.course.CourseDto;
import com.ragnar.MySchoolManagement.user.UserDTO;

public class EnrollmentDto {

	private UserDTO user;
	private LocalDateTime dateAndTimeEnrolled;

	public EnrollmentDto(Enrolment enrolment) {
		this.user = new UserDTO(enrolment.getUser());
		this.dateAndTimeEnrolled = enrolment.getCreatedAt();
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public LocalDateTime getDateAndTimeEnrolled() {
		return dateAndTimeEnrolled;
	}

	public void setDateAndTimeEnrolled(LocalDateTime dateAndTimeEnrolled) {
		this.dateAndTimeEnrolled = dateAndTimeEnrolled;
	}

}
