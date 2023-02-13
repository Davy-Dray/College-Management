package com.ragnar.MySchoolManagement.grade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {

	List<StudentGrade> findGradeByStudentId(Long id);
	
	 @Query("SELECT g.grade FROM StudentGrade g WHERE g.student.id = :studentId")
	 List<Double> findGradesByStudentId(@Param("studentId") Long studentId);
}
