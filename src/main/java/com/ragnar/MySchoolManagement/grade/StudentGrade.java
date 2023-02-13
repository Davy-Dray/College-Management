package com.ragnar.MySchoolManagement.grade;


import com.ragnar.MySchoolManagement.course.Course;
import com.ragnar.MySchoolManagement.user.student.Student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity(name = "Grade")
public class StudentGrade {

	
	@Id
	@SequenceGenerator(
			name = "grade_sequence",
			sequenceName =  "grade_sequence",
			allocationSize = 1			
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator =  "grade_sequence"
	)
	@Column(
			name = "id",
			updatable = false
	)
	private Long id;
	
	
	@ManyToOne
	private Student student;
	
	@OneToOne
	private Course course;
	
	private double grade;

	public StudentGrade(Student student, Course course, double grade) {
		this.student = student;
		this.course = course;
		this.grade = grade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	
	
	
}
