package com.ragnar.MySchoolManagement.constanst;


import static com.ragnar.MySchoolManagement.constanst.Authority.*;

public enum RoleConstants {

	ROLE_TEACHER(TEACHER_AUTHORITIES),
	ROLE_STUDENT(STUDENT_AUTHORITIES),
	ROLE_MANAGER(MANAGER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

	
	private String[]authorities;
	
	
	RoleConstants(String...authorities){
		
		this.authorities = authorities;
	}


	public String[] getAuthorities() {
		return authorities;
	}


}
