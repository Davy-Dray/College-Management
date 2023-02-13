package com.ragnar.MySchoolManagement.auth;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ragnar.MySchoolManagement.user.User;
import com.ragnar.MySchoolManagement.user.UserPrincipal;
import com.ragnar.MySchoolManagement.user.UserService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	private final AuthenticationManager authManager;
	private final TokenProvider tokenProvider;
	private final UserService userService;

	public AuthController(UserService userService, AuthenticationManager authManager, TokenProvider tokenProvider) {
		this.userService = userService;
		this.authManager = authManager;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping()
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
		authenticate(request.getEmail(), request.getPassword());

		Optional<User> loginUser = userService.findByUsername((request.getEmail()));

		User myUser = loginUser.get();

		UserPrincipal userPrincipal = new UserPrincipal(myUser);

		String token = tokenProvider.generateToken(userPrincipal);
		JwtResponse jwtAuthResponse = new JwtResponse();
		jwtAuthResponse.setJwtToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	private void authenticate(String username, String password) {

		authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}
