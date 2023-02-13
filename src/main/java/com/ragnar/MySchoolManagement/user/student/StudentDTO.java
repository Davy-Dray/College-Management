package com.ragnar.MySchoolManagement.user.student;

import java.util.Arrays;

public class StudentDTO {

	Long id;
	String email;
	String firstname;
	String lastname;
	String[] role;
	Integer age;

	public StudentDTO(Student user) {

		this.firstname = user.getFirstName();
		this.lastname = user.getLastName();
		this.id = user.getId();
		this.role = user.getAuthorities();
		this.email = user.getEmail();
		this.age = user.getAge();

	}


	public StudentDTO(Long id, String email, String firstname, String lastname, String[] role, Integer age) {
		super();
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
		this.age = age;
	}


	public StudentDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	

}
