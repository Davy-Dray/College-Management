package com.ragnar.MySchoolManagement.user;
import java.util.function.Function;

import org.springframework.stereotype.Service;

@Service
public class UserDTOmapper implements Function<User , UserDTO> {

	@Override
	public UserDTO apply(User user) {
		// TODO Auto-generated method stub
		return new UserDTO(user.getId(),
				           user.getEmail(),
				           user.getFirstName(),
				           user.getLastName(), 
				           user.getId_Card().getCardNumber(),
				           user.getAuthorities()
				           
      );
	}
}
