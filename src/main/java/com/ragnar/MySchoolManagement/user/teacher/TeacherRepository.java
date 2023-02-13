package com.ragnar.MySchoolManagement.user.teacher;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TeacherRepository extends JpaRepository<Teacher,Long> {

	Optional<Teacher> findByEmail(String email);

}
