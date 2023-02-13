package com.ragnar.MySchoolManagement.user.IDcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserRepository;

@Service
public class UserCardServiceImpl implements UserCardService {

	Faker faker = new Faker();

	private final UserRepository userRepository;
	
	@Autowired
	UserCardRepository cardRepository;

	public UserCardServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User createUserIdCard(Long userId) {
		
		User user = userRepository.findById(userId).orElseThrow();
		
		String cardNumber = faker.numerify(user.getFirstName()+"123");

		User_ID_Card idCard = new User_ID_Card(cardNumber, user);

		user.setUserID(idCard);

		return userRepository.save(user);
	}

}
