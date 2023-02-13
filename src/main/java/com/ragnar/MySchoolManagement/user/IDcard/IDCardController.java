package com.ragnar.MySchoolManagement.user.IDcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.student.Student;

@RestController
public class IDCardController {

	@Autowired
	UserCardService cardService;

	@PostMapping("/create-card/{id}")
	public User createIdCard(@PathVariable Long id) {
		return cardService.createUserIdCard(id);
	}
}
