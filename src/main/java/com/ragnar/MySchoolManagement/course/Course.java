package com.ragnar.MySchoolManagement.course;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ragnar.MySchoolManagement.entenrollment.Enrolment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity(name="Course")
@Table(name = "course")
public class Course {

	
	@Id
	@SequenceGenerator(
			name = "course_sequence",
			sequenceName =  "course_sequence",
			allocationSize = 1			
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator =  "course_sequence"
	)
	@Column(
			name = "id",
			updatable = false
	)
	private Long id;
	
	
	private boolean isCancelled;
	

	@Column(
			name = "course_title",
			nullable = false,
			columnDefinition = "TEXT",
			unique = true
			
	)
	private String courseTitle;
	
	@Column(name = "maximumStudents",
			nullable = false
    )
	private Integer maximumStudents;
	
	@Column(name = "minimumStudents",
			nullable = false
    )
	private Integer minimumStudents;

	
	
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "course", orphanRemoval = true,fetch = FetchType.EAGER)
	@JsonIgnore
    private List<Enrolment> enrolments = new ArrayList<>();

	
	public Course(String courseTitle, Integer maximumStudents, Integer minimumStudents) {
		this.isCancelled = false;
		this.courseTitle = courseTitle;
		this.maximumStudents = maximumStudents;
		this.minimumStudents = minimumStudents;
	}

	public Course() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public Integer getMaximumStudents() {
		return maximumStudents;
	}

	public void setMaximumStudents(Integer maximumStudents) {
		this.maximumStudents = maximumStudents;
	}

	public Integer getMinimumStudents() {
		return minimumStudents;
	}

	public void setMinimumStudents(Integer minimumStudents) {
		this.minimumStudents = minimumStudents;
	}

	
	public List<Enrolment> getEnrolments() {
		return enrolments;
	}

	public void setEnrolments(List<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	
	@Override
	public String toString() {
		return "Course [id=" + id + ", isCancelled=" + isCancelled + ", courseTitle=" + courseTitle
				+ ", maximumStudents=" + maximumStudents + ", minimumStudents=" + minimumStudents + "]";
	}
	
	 public void addEnrolment(Enrolment enrolment) {
	        if (!enrolments.contains(enrolment)) {
	            enrolments.add(enrolment);
	        }
	    }

	    public void removeEnrolment(Enrolment enrolment) {
	        enrolments.remove(enrolment);
	    }

}
