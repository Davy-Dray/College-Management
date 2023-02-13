package com.ragnar.MySchoolManagement.user.student;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ragnar.MySchoolManagement.entenrollment.Enrolment;

public interface StudentRepository extends JpaRepository<Student,Long> {
	
	public Enrolment findByEnrolmentsUserId(Long id);
 }
