package com.ragnar.MySchoolManagement.user.student;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ragnar.MySchoolManagement.course.Course;
import com.ragnar.MySchoolManagement.course.CourseService;
import com.ragnar.MySchoolManagement.entenrollment.Enrolment;



@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final CourseService courseService;	
	private final StudentDTOmapper dtOmapper;


	public StudentServiceImpl(StudentRepository studentRepository, 
			                 CourseService courseService,
			                 StudentDTOmapper dtOmapper
			                 ) {
		this.studentRepository = studentRepository;
		this.courseService = courseService;
		this.dtOmapper = dtOmapper;
	}

	public List<Course> getCoursesForStudent(Long studentId) {
 	    Student student = studentRepository.findById(studentId).orElseThrow();
 	    return student.getEnrolments().stream()
 		              .map(Enrolment::getCourse)
 		              .collect(Collectors.toList());
 		}
     
     @Override
 	 public Page<Student> findAllStudent() {
 		PageRequest pageRequest = PageRequest.of(
 			       0,
 			       5, 
 			       Sort.by(Sort.Direction.ASC,"firstName"));
 	  Page<Student> page = studentRepository.findAll(pageRequest);
 	  return page;
 	}
     
    @Override
 	public Student findByStudentId(Long id) {
 		return studentRepository.findById(id).orElseThrow();
 	}

 	@Override
 	public void deleteStudent(Long id) {
           studentRepository.deleteById(id);		
 	}

	@Override
	public int getStudentCourseCount(String email) {
		return courseService.getCoursesForUser(email).size();
	}

	@Override
	public StudentDTO findByStudentIds(Long studentId) {
		 return studentRepository
				 .findById(studentId)
				 .map(dtOmapper).orElseThrow();
	}
	
}