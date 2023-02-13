package com.ragnar.MySchoolManagement.grade;

import java.util.List;

public interface GradeService {

	public StudentGrade addGrade(GradeForm form);
	
	public StudentGrade updateGrade(GradeForm form,Long gradeId);
		
	public List<StudentGrade> getStudentGrades(Long studentId);
}
