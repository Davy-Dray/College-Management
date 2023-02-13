package com.ragnar.MySchoolManagement.course;

public class CourseDto {

	String courseTitle;

	public CourseDto(String courseTitle) {
		this.courseTitle = courseTitle;

	}

	public CourseDto(Course course) {
		this.courseTitle = course.getCourseTitle();

	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	@Override
	public String toString() {
		return "CourseDto [courseTitle=" + courseTitle + "]";
	}

}