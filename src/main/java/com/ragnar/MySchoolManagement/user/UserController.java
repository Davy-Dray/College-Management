package com.ragnar.MySchoolManagement.user;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public UserDTO findUserId(@PathVariable Long id) {
		return userService.findUserById(id);
	}

	@PostMapping("/student")
	public ResponseEntity<?> registerStudent(
			@RequestBody UserRegistrationRequest request) {
		userService.registerStudent(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/teacher")
	public ResponseEntity<?> registerTeacher(
			@RequestBody UserRegistrationRequest request) {
		userService.registerTeacher(request);
		return ResponseEntity.ok().build();
	}
	@GetMapping
	public Page<UserDTO> getAllUsers() {
		return userService.findAllUsers();
	}

	@PutMapping("/{id}")
	public UserDTO updateUser(
			@RequestBody UserUpdateRequest request,
			@PathVariable Long id) {
		return userService.updateUser(id, request);
	}

}
