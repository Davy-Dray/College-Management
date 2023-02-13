package com.ragnar.MySchoolManagement.entenrollment;

public interface EnrolmentService {

	public boolean unEnrolFromCourse(Long courseId, Long userID);

	public void enrolToCourse(String email, Long courseID);


}
