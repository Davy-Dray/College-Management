package com.ragnar.MySchoolManagement.grade;

import java.util.List;

import com.ragnar.MySchoolManagement.course.Course;
import com.ragnar.MySchoolManagement.course.CourseRepository;
import com.ragnar.MySchoolManagement.user.student.Student;
import com.ragnar.MySchoolManagement.user.student.StudentRepository;
import com.ragnar.MySchoolManagement.user.student.StudentService;
import com.ragnar.MySchoolManagement.user.student.StudentStatus;

import jakarta.persistence.EntityNotFoundException;

public class GradeServiceImpl implements GradeService{

	private static final int CUT_OFF_MARK = 60;
	private final StudentGradeRepository gradeRepository;	
	private final StudentService studentService;
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	
	public GradeServiceImpl(StudentGradeRepository gradeRepository, StudentService studentService,
			CourseRepository courseRepository, StudentRepository studentRepository) {
		this.gradeRepository = gradeRepository;
		this.studentService = studentService;
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public StudentGrade addGrade(GradeForm form) {	
		Student student = studentService.findByStudentId(form.getStudentId());
		Course  course = courseRepository.findById(form.getCourseId()).orElseThrow();
		return gradeRepository.save(new StudentGrade(student, course, form.getGrade()));
	}

	@Override
	public StudentGrade updateGrade(GradeForm form,Long gradeId) {		
		StudentGrade grade = gradeRepository.findById(gradeId).orElseThrow(EntityNotFoundException::new);
		grade.setGrade(form.getGrade());
		return  gradeRepository.save(grade);
	}

	@Override
	public List<StudentGrade> getStudentGrades(Long studentId) {
		return gradeRepository.findGradeByStudentId(studentId);
	}

	public double getAverageGradeForStudent(Long studentId)  {
	    List<Double> grades = gradeRepository.findGradesByStudentId(studentId);
	    double averageGrade = calculateAverage(grades);
	    updateStudentStatus(studentId, averageGrade);
	    return averageGrade;
	}


	private double calculateAverage(List<Double> grades) {
	    double sum = grades.stream().mapToDouble(Double::doubleValue).sum();
	    return sum / grades.size();
	}

	private void updateStudentStatus(Long studentId, double averageGrade) {
	    studentRepository.findById(studentId).ifPresent(student -> {
	        StudentStatus studentStatus = averageGrade < CUT_OFF_MARK ? StudentStatus.PROBATION : StudentStatus.PROMOTED;
	        student.setStudentStatus(studentStatus);
	        studentRepository.save(student);
	    });
	}

	
	
}
