package com.ragnar.MySchoolManagement.auth;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequest {

	
	String email;
	
	String password;
}
