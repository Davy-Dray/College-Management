package com.ragnar.MySchoolManagement.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException() {
	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
	
}
