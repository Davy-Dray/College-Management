package com.ragnar.MySchoolManagement.entenrollment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrolmentRepository extends JpaRepository<Enrolment, EnrolmentId> {

//	Enrolment findByCourseIdAndUserI(Long courseid, Long userId);

	Enrolment findByEnrolmentId_UserIdAndEnrolmentId_CourseId(Long studentId, Long courseId);

	@Query("SELECT e FROM Enrolment e WHERE e.course.id = :courseId AND e.user.role = :role")
    List<Enrolment> findByCourseIdAndUserRole(@Param("courseId") Long courseId, @Param("role") String role);

	List<Enrolment> findByEnrolmentId_CourseId(Long courseId);


	//List<Enrolment> findByCourseIdAndUserRole(Long courseId, String name);
	
	
}
