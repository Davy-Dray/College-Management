package com.ragnar.MySchoolManagement.course;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ragnar.MySchoolManagement.constanst.RoleConstants;
import com.ragnar.MySchoolManagement.entenrollment.EnrollmentDto;
import com.ragnar.MySchoolManagement.entenrollment.Enrolment;
import com.ragnar.MySchoolManagement.entenrollment.EnrolmentDTOmapper;
import com.ragnar.MySchoolManagement.entenrollment.EnrolmentRepository;
import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseServiceImpl implements CourseService {

	private final EnrolmentDTOmapper enrolmentDTOmapper;
	private final CourseDtomapper courseDtOmapper;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	private final EnrolmentRepository enrolmentRepository;

	public CourseServiceImpl(CourseRepository courseRepository,
			UserRepository userRepository,
			EnrolmentRepository enrolmentRepository,
			CourseDtomapper courseDtOmapper,
			EnrolmentDTOmapper enrolmentDTOmapper) {
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
		this.enrolmentRepository = enrolmentRepository;
		this.courseDtOmapper = courseDtOmapper;
		this.enrolmentDTOmapper = enrolmentDTOmapper;
	}

	@Override
	public Course createCourse(RegisterCourse courseDto) {
		Course course = new Course(
				courseDto.getCourseTitle(),
				courseDto.getMaximumStudents(),
				courseDto.getMinimumStudents());
		courseRepository.save(course);
		return course;
	}

	@Override
	public CourseDto findCourseById(Long courseId) {
		return courseRepository.findById(courseId)
				.map(courseDtOmapper)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("course id:[%s] not found", courseId)));
	}

	@Override
	public List<CourseDto> getCoursesForUser(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return user.getEnrolments().stream()
				.map(enrolment -> courseDtOmapper.apply(enrolment.getCourse()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean isCourseCancelled(Long courseId) {
		Course course = courseRepository.findById(courseId).orElseThrow();
		long enrolmentCount = getAllStudentsInACourse(courseId).size();
		boolean isCancelled = enrolmentCount >= course.getMaximumStudents();
		course.setCancelled(isCancelled);
		return isCancelled;
	}

	private List<EnrollmentDto> getEnrollmentsByRole(Long courseId, String role) {
		List<Enrolment> enrollments = enrolmentRepository
				.findByCourseIdAndUserRole(
						courseId,
						role);

		List<EnrollmentDto> response = enrollments.stream()
				.map(enrolmentDTOmapper)
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public List<EnrollmentDto> getAllTeachersInACourse(Long courseId) {
		return getEnrollmentsByRole(courseId, RoleConstants.ROLE_TEACHER.name());
	}

	@Override
	public List<EnrollmentDto> getAllStudentsInACourse(Long courseId) {
		return getEnrollmentsByRole(courseId,
				RoleConstants.ROLE_STUDENT.name());
	}

	@Override
	public List<EnrollmentDto> getAllUsersInAcourse(Long courseId) {
		List<Enrolment> enrollments = enrolmentRepository.findByEnrolmentId_CourseId(courseId);

		List<EnrollmentDto> response = enrollments.stream()
				.map(enrolmentDTOmapper)
				.collect(Collectors.toList());
		return response;
	}

}
