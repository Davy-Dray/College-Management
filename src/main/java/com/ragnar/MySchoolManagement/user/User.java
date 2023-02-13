package com.ragnar.MySchoolManagement.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.MySchoolManagement.entenrollment.Enrolment;
import com.ragnar.MySchoolManagement.user.IDcard.User_ID_Card;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(name = "user_email_unique", columnNames = "email") })
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
	private String firstName;

	@Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
	private String lastName;

	@Column(name = "email", nullable = false, columnDefinition = "TEXT")
	private String email;

	@Column(name = "age", nullable = false)
	private Integer age;

	@JsonIgnore
	private String password;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "user", orphanRemoval = true,fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Enrolment> enrolments = new ArrayList<>();
	private Date lastLogin;

	private Date lastloginDateDisplay;

	private Date joinDate;

	private boolean isActive; 

	private boolean isNotlocked;

	private String role;
	
	private String[] authorities;
	  @OneToOne(
	            mappedBy = "user",
	            orphanRemoval = true,
	            cascade = {CascadeType.ALL},
	            fetch = FetchType.LAZY

	    )
  //  @JoinColumn(name = "card_id")
	  @LazyToOne(LazyToOneOption.NO_PROXY)
	private User_ID_Card id_Card;

	public User() {
	}

	public User(UserRegistrationRequest newUserForm, String encryptedPassword) {
		this.setFirstName(newUserForm.getFirstName());
		this.setLastName(newUserForm.getLastName());
		this.setEmail(newUserForm.getEmail());
		this.password = encryptedPassword;
		this.setAge(newUserForm.getAge());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastloginDateDisplay() {
		return lastloginDateDisplay;
	}

	public void setLastloginDateDisplay(Date lastloginDateDisplay) {
		this.lastloginDateDisplay = lastloginDateDisplay;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isNotlocked() {
		return isNotlocked;
	}

	public void setNotlocked(boolean isNotlocked) {
		this.isNotlocked = isNotlocked;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	public List<Enrolment> getEnrolments() {
		return enrolments;
	}

	public User_ID_Card getId_Card() {
		return id_Card;
	}

	public void setId_Card(User_ID_Card id_Card) {
		this.id_Card = id_Card;
	}
	

	public void setUserID(User_ID_Card id_Card) {
		this.id_Card = id_Card;
		id_Card.setUser(this);
	}
	
	

	public void setEnrolments(List<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public void addEnrolment(Enrolment enrolment) {
		if (!enrolments.contains(enrolment) && !enrolment.getCourse().isCancelled()) {
			enrolments.add(enrolment);
		}
	}

	public void removeEnrolment(Enrolment enrolment) {
		enrolments.remove(enrolment);
	}

}
