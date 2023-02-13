package com.ragnar.MySchoolManagement.entenrollment;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EnrolmentId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;

    public EnrolmentId(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public EnrolmentId() {
    }

    public Long getuserId() {
        return userId;
    }

    public void setuserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrolmentId that = (EnrolmentId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }
}
