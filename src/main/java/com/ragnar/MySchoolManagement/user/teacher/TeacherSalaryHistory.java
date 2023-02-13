package com.ragnar.MySchoolManagement.user.teacher;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class TeacherSalaryHistory {

	@Id
	@SequenceGenerator(name = "teacher_salary_sequence", sequenceName = "teacher_salary_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_salary_sequence")
	@Column(name = "id", updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Teacher teacher;

	private double salary;

    private LocalDateTime createdAt;

	public TeacherSalaryHistory() {

	}

	public TeacherSalaryHistory(Teacher teacher, double salary) {
		super();
		this.teacher = teacher;
		this.salary = salary;
		this.createdAt=LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
