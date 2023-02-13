package com.ragnar.MySchoolManagement.entenrollment;


import java.time.LocalDateTime;

import com.ragnar.MySchoolManagement.course.Course;
import com.ragnar.MySchoolManagement.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId enrolmentId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "enrolment_user_id_fk" 
            )
    )
    private User user;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(
                    name = "enrolment_course_id_fk"
            )
    )
    private Course course;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;


    public Enrolment(EnrolmentId id,
    		         User user,
                     Course course,
                     LocalDateTime createdAt) {
        this.enrolmentId = id;
        this.user = user;
        this.course = course;
        this.createdAt = createdAt;
    }

    public Enrolment() {
    }

    public EnrolmentId getEnrolmentId() {
        return enrolmentId;
    }

    public void setEnrolmentId(EnrolmentId enrolmentId) {
        this.enrolmentId = enrolmentId;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

	@Override
	public String toString() {
		return "Enrolment [id=" + enrolmentId + ", student=" + user + ", course=" + course + ", createdAt=" + createdAt + "]";
	}
    
}
