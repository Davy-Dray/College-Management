package com.ragnar.MySchoolManagement.user;

import java.util.Arrays;

public class UserDTO {

	Long id;
	String email;
	String firstname;
	String lastname;
	String id_cardNumber;
	String[] role;

	public UserDTO(User user) {

		this.firstname = user.getFirstName();
		this.lastname = user.getLastName();
		this.id = user.getId();
		this.role = user.getAuthorities();
		this.email = user.getEmail();
		this.id_cardNumber = user.getId_Card().getCardNumber();

	}



	public UserDTO(Long id, String email, String firstname, String lastname, String id_cardNumber, String[] role) {
		super();
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.id_cardNumber = id_cardNumber;
		this.role = role;
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

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", role=" + Arrays.toString(role) + "]";
	}

}
