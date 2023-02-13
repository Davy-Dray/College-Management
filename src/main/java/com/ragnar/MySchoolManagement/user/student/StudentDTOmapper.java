package com.ragnar.MySchoolManagement.user.student;
import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class StudentDTOmapper implements Function<Student , StudentDTO> {

	@Override
	public StudentDTO apply(Student user) {
		// TODO Auto-generated method stub
		return new StudentDTO(user.getId(),
				           user.getEmail(),
				           user.getFirstName(),
				           user.getLastName(), 
				           user.getAuthorities(),
				           user.getAge()
       );
	}
}
