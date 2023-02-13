package com.ragnar.MySchoolManagement.course;

import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class CourseDtomapper implements Function<Course, CourseDto> {

	@Override
	public CourseDto apply(Course course) {
		return new CourseDto(course.getCourseTitle()

		);

	}
}