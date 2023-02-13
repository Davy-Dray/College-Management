package com.ragnar.MySchoolManagement.constanst;

public class Authority {

	public static final String[] USER_AUTHORITIES = { "user:read" };
	public static final String[] HR_AUTHORITIES = { "user:read", "user:update" };
	public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:update" };
	public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" };
	public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" };
	
	
	public static final String[] TEACHER_AUTHORITIES = { "role:teacher"};
	public static final String[] STUDENT_AUTHORITIES = { "role:student"};



}
