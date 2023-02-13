package com.ragnar.MySchoolManagement.user;


import java.util.Optional;

import org.springframework.data.domain.Page;
import com.ragnar.MySchoolManagement.exceptions.EmailAlreadyExistsException;

public interface UserService {

	public UserDTO updateUser(Long id,  UserUpdateRequest updateReques);

	public Page<UserDTO> findAllUsers();

	public UserDTO findUserById(Long id);

	void registerStudent(UserRegistrationRequest request) throws EmailAlreadyExistsException;

	void registerTeacher(UserRegistrationRequest request) throws EmailAlreadyExistsException;

	void deleteUser(Long id);

	Optional<User> findByUsername(String username);


}
