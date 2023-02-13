package com.ragnar.MySchoolManagement.user.teacher;

import org.springframework.stereotype.Service;

@Service
public class TeacherImpl implements TeacherService {

	private final TeacherRepository teacherRepository;
       
	public TeacherImpl(TeacherRepository teacherRepository){	
		this.teacherRepository = teacherRepository;		
	}

	
}