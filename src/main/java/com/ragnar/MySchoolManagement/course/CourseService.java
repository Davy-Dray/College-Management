package com.ragnar.MySchoolManagement.course;

import java.util.List;

import com.ragnar.MySchoolManagement.entenrollment.EnrollmentDto;

public interface CourseService {

	public CourseDto findCourseById(Long courseId);

	public List<CourseDto> getCoursesForUser(String email);

	boolean isCourseCancelled(Long courseId);

	public List<EnrollmentDto> getAllStudentsInACourse(Long courseId);

	public List<EnrollmentDto> getAllTeachersInACourse(Long courseId);

	public List<EnrollmentDto> getAllUsersInAcourse(Long courseId);

	Course createCourse(RegisterCourse courseDto);

}
