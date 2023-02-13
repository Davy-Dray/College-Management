package com.ragnar.MySchoolManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;
import com.ragnar.MySchoolManagement.course.Course;
import com.ragnar.MySchoolManagement.course.CourseDto;
import com.ragnar.MySchoolManagement.course.CourseRepository;
import com.ragnar.MySchoolManagement.course.CourseService;
import com.ragnar.MySchoolManagement.entenrollment.EnrolmentRepository;
import com.ragnar.MySchoolManagement.entenrollment.EnrolmentService;
import com.ragnar.MySchoolManagement.grade.StudentGradeRepository;
import com.ragnar.MySchoolManagement.user.UserRegistrationRequest;
import com.ragnar.MySchoolManagement.user.UserService;
import com.ragnar.MySchoolManagement.user.IDcard.UserCardService;
import com.ragnar.MySchoolManagement.user.student.StudentIdRepository;
import com.ragnar.MySchoolManagement.user.student.StudentRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	@Autowired	
	UserCardService cardService;
	
	@Autowired
	CourseService courseService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	EnrolmentService enrolmentService;
	@Autowired
	UserService userservice;

	@Autowired
	StudentGradeRepository gradeRepository;

	@Autowired
	EnrolmentRepository enrolmentRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentIdRepository idRepository;

	@Autowired
	CourseRepository courseRepository;

	@Override
	public void run(String... args) throws Exception {

		Faker faker = new Faker();

		for (int i = 0; i < 3; i++) {
			String coursename = faker.programmingLanguage().name();
			Course course = new Course(coursename, 3, 6);
			
			courseRepository.save(course);
		}
		

		
		
	//	for (int i = 0; i < 1; i++) {

			String firstname = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = String.format("%s.%s@gmail.com", firstname, lastName);

			String cardNumber = faker.numerify("aa");

			UserRegistrationRequest request = new UserRegistrationRequest();
			request.setLastName(lastName);
			request.setFirstName(firstname);
			request.setEmail(email);
			request.setAge(faker.number().numberBetween(17, 55));
			request.setPassword(encoder.encode("david"));
			

			userservice.registerStudent(request);
			
			enrolmentService.enrolToCourse(email, 1L);
			
			enrolmentService.enrolToCourse(email, 2L);
			
			enrolmentService.enrolToCourse(email, 3L);

			

	//	}
		
//		for (int i = 0; i < 1; i++) {
//
//			String firstname = faker.name().firstName();
//			String lastName = faker.name().lastName();
//			String email = String.format("%s.%s@gmail.com", firstname, lastName);
//
//			String cardNumber = faker.numerify("aa");
//
//			UserRegistrationRequest request = new UserRegistrationRequest();
//			request.setLastName(lastName);
//			request.setFirstName(firstname);
//			request.setEmail(email);
//			request.setAge(faker.number().numberBetween(17, 55));
//			request.setPassword(encoder.encode("david"));
//			request.setCurrentSalary(100000D);
//
//			userservice.registerTeacher(request);
//			
//			enrolmentService.enrolToCourse(email, 1L);
//			
//			enrolmentService.enrolToCourse(email, 2L);
//
//		}
//		
//
		List<CourseDto>  coursesOffered =   courseService.getCoursesForUser(email);
		for (CourseDto courseDto : coursesOffered) {
			System.out.println("student "+courseDto);

		}
//		
		
		cardService.createUserIdCard(1L);

//		List<CourseDto>  coursesOffered1 =   courseService.getCoursesForUser(2L);
//		
//		for (CourseDto courseDto1 : coursesOffered1) {
//			System.out.println("teacher "+courseDto1);
//
//		}
		
//		Page<UserDTO> page = userservice.findAllUsers();
//		for (UserDTO userDTO : page) {
//			System.out.println(userDTO);
//
//		}

//
//String fakebook = faker.book().title();
//		
//		Student student =new Student(firstname, lastName, email, faker.number().numberBetween(17, 55));
//		
////		student.addBook(new Book(fakebook,LocalDateTime.now().minusDays(4)));
////		student.addBook(new Book(fakebook,LocalDateTime.now().minusDays(5)));
////
////		
////		
//	Student_ID_Card student_ID_Card = new Student_ID_Card(cardNumber, student);
//		student.setCard(student_ID_Card);
//		studentRepository.save(student);

//		userservice.enrollStudentToCourse(1L, 1L);
//		userservice.enrollStudentToCourse(2L, 1L);
//
//		
//		student.addEnrolment(new Enrolment(
//				new EnrolmentId(1L,1L), 
//				student, 
//				course, 
//				LocalDateTime.now().minusDays(4)));
//		
//		
//		student.addEnrolment(new Enrolment(
//				new EnrolmentId(1L,1L), 
//				student, 
//				course2, 
//				LocalDateTime.now().minusDays(4)));
//		
//	
//		studentRepository.save(student);
//		
//		gradeRepository.save(new StudentGrade(student, course, 100));
//	    gradeRepository.save(new StudentGrade(student, course, 12));
//
//
//		studentRepository.findById(1L)
//		                  .ifPresent(s -> {
//		                	  List<Book>  books= student.getStudentBooks();
//		                	  books.forEach(book ->{
//		                		  System.out.println(s.getFirstName() +" took "+ book.getBookName());
//		                	  });
//		                	  
//		                  });
		// userservice.unenrollFromCourse(1L, 1L);
//		Enrolment enrolment = enrolmentRepository.findByCourseIdAndStudentId(1L, 1L);
//	    student.removeEnrolment(enrolment);
//	  

	}

//
//	private void sorting() {
//		studentRepository.findAll(Sort.by(Sort.Direction.ASC,"firstName"))
//		                 .forEach(
//		                     student -> System.out.println(student.getFirstName()));
//	}
}