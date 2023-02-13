package com.ragnar.MySchoolManagement.entenrollment;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ragnar.MySchoolManagement.constanst.RoleConstants;
import com.ragnar.MySchoolManagement.course.Course;
import com.ragnar.MySchoolManagement.course.CourseRepository;
import com.ragnar.MySchoolManagement.course.CourseService;
import com.ragnar.MySchoolManagement.exceptions.AlreadyEnrolledException;
import com.ragnar.MySchoolManagement.exceptions.CourseCancelledException;
import com.ragnar.MySchoolManagement.exceptions.CourseLimitReachedException;
import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserRepository;
import com.ragnar.MySchoolManagement.user.student.Student;
import com.ragnar.MySchoolManagement.user.student.StudentService;
import com.ragnar.MySchoolManagement.user.teacher.Teacher;
import com.ragnar.MySchoolManagement.user.teacher.TeacherRepository;
import com.ragnar.MySchoolManagement.user.teacher.TeacherSalaryHistory;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

	private static final int MAX_AMOUNT_OF_COURSES = 6;
	private static final double ONE_TIME_BONUS = 10_000;
	private static final int MINIMUM_COURSES_FOR_BONUS = 1;
	private final EnrolmentRepository enrolmentRepository;
	private final StudentService studentService;
	private final CourseService courseService;
	private final UserRepository userRepository;
	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;

	public EnrolmentServiceImpl(StudentService studentService,
			CourseRepository courseRepository,
			EnrolmentRepository enrolmentRepository,
			CourseService courseService,
			UserRepository userRepository,
			TeacherRepository teacherRepository) {
		this.studentService = studentService;
		this.enrolmentRepository = enrolmentRepository;
		this.courseService = courseService;
		this.userRepository = userRepository;
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public boolean unEnrolFromCourse(Long courseId, Long userID) {
		User user = userRepository.findById(userID).orElseThrow(EntityNotFoundException::new);
		Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);
		Enrolment enrolment = enrolmentRepository.
				              findById(  
				             new EnrolmentId(
				             userID, courseId
				             )
				         )
				.orElseThrow(EntityNotFoundException::new);
		user.getEnrolments().remove(enrolment);
		course.getEnrolments().remove(enrolment);
		enrolmentRepository.delete(enrolment);
		return true;

	}
	@Override
	public void enrolToCourse(String email,Long courseID) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (!optionalUser.isPresent())
			throw new UsernameNotFoundException(String.format("[%s] not found", email));

		User myUser = optionalUser.get();
		if (myUser.getRole().equals(RoleConstants.ROLE_TEACHER.name()))
			enrolTeacherToCourse(myUser.getId(), courseID);
		if (myUser.getRole().equals(RoleConstants.ROLE_STUDENT.name()))
			enrolStudentToCourse(myUser.getId(), courseID);
	}

	private void enrolStudentToCourse(Long studentId, Long courseId) {
		Student student = studentService.findByStudentId(studentId);
		Course course = courseRepository.findById(courseId).orElseThrow();
		EnrolmentId enrolmentId = new EnrolmentId(studentId, courseId);

		if (courseService.isCourseCancelled(courseId))
			throw new CourseCancelledException(String.format("[%s]  is cancelled", course.getCourseTitle()));
		if (isUserEnrolledInCourse(studentId, courseId))
			throw new AlreadyEnrolledException(String.format("already enrolled in [%s]", course.getCourseTitle()));
		if (studentService.getStudentCourseCount(student.getEmail()) >= MAX_AMOUNT_OF_COURSES)
			throw new CourseLimitReachedException(String.format("[%s] course limit reached", course.getCourseTitle()));

		Enrolment enrolment = new Enrolment(enrolmentId, student, course, LocalDateTime.now());
		student.addEnrolment(enrolment);
		course.addEnrolment(enrolment);
		userRepository.save(student);
	}

	private void enrolTeacherToCourse(Long teacherID, Long courseId) {
		Teacher teacher = teacherRepository.findById(teacherID).orElseThrow(EntityNotFoundException::new);
		Course course = courseRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);

		if (courseService.isCourseCancelled(courseId))
			throw new CourseCancelledException(String.format("[%s]  is cancelled", course.getCourseTitle()));
		if (isUserEnrolledInCourse(teacherID, courseId))
			throw new AlreadyEnrolledException(String.format("already enrolled in [%s]", course.getCourseTitle()));

		EnrolmentId enrolmentId = new EnrolmentId(teacherID, courseId);
		Enrolment enrolment = new Enrolment(enrolmentId, teacher, course, LocalDateTime.now());
		teacher.addEnrolment(enrolment);
		userRepository.save(teacher);

		if (shouldReceiveBonus(teacher.getEmail())) {
			teacher.setHasRecivedBonus(true);
			TeacherSalaryHistory history = new TeacherSalaryHistory(teacher, ONE_TIME_BONUS);
			teacher.addSalaryHistory(history);
			userRepository.save(teacher);
		}
	}

	private boolean shouldReceiveBonus(String email) {
		long numCourses = courseService.getCoursesForUser(email).size();
		Teacher teacher = teacherRepository.findByEmail(email).orElseThrow();
		return numCourses >= MINIMUM_COURSES_FOR_BONUS && !teacher.hasRecivedBonus();
	}

	private boolean isUserEnrolledInCourse(Long studentId, Long courseId) {
		return enrolmentRepository.
		       findByEnrolmentId_UserIdAndEnrolmentId_CourseId
			           (studentId, courseId) != null;
	}

}
