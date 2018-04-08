package com.pomodoro.integration.manuscript.domain;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthenticationException(){
		super("Token is not valid, please login to continue utilziing Manuscript features");
	}

}
