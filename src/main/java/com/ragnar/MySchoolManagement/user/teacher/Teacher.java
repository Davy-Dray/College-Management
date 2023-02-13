package com.ragnar.MySchoolManagement.user.teacher;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserRegistrationRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Teacher")
@Table(name = "teacher")
public class Teacher extends User {

	private double currentSalary;

	private boolean hasRecivedBonus;

	@OneToMany(mappedBy = "teacher", 
			   orphanRemoval = true, 
			   fetch = FetchType.EAGER, 
			   cascade = { CascadeType.ALL}
	)
	@JsonIgnore
	private List<TeacherSalaryHistory> teacherSalaryHistory = new ArrayList<>();

	public Teacher() {

	}

	public Teacher(UserRegistrationRequest userFormDTO, String encryptedPassword) {
		super(userFormDTO, encryptedPassword);
		this.setHasRecivedBonus(false);
		this.currentSalary = userFormDTO.getCurrentSalary();
	}

	public List<TeacherSalaryHistory> getTeacherSalaryHistory() {
		return teacherSalaryHistory;
	}

	public void setTeacherSalaryHistory(List<TeacherSalaryHistory> teacherSalaryHistory) {
		this.teacherSalaryHistory = teacherSalaryHistory;
	}

	public boolean isHasRecivedBonus() {
		return hasRecivedBonus;
	}

	public double getCurrentSalary() {
		return currentSalary;
	}

	public void setCurrentSalary(double currentSalary) {
		this.currentSalary = currentSalary;
	}

	public boolean hasRecivedBonus() {
		return hasRecivedBonus;
	}

	public void setHasRecivedBonus(boolean hasRecivedBonus) {
		this.hasRecivedBonus = hasRecivedBonus;
	}

	public void addSalaryHistory(TeacherSalaryHistory salaryHistory) {
		teacherSalaryHistory.add(salaryHistory);
		salaryHistory.setTeacher(this);
	}
}
