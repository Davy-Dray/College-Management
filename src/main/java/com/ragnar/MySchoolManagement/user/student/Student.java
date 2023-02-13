package com.ragnar.MySchoolManagement.user.student;

import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserRegistrationRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity(name = "Student")
@Table(name = "student")
public class Student extends User {


	@Enumerated(EnumType.STRING)
	private StudentStatus studentStatus;

	public Student() {

	}

	public Student(UserRegistrationRequest userFormDTO, String encryptedPassword) {
		super(userFormDTO, encryptedPassword);
		this.setStudentStatus(StudentStatus.FRESHER);
	}

	public StudentStatus getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(StudentStatus studentStatus) {
		this.studentStatus = studentStatus;
	}

}
