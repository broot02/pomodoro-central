package com.pomodoro.integration.manuscript.domain;

import java.util.List;

public class AmbiguousAuthenticationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 464724711450255556L;
	private final List<String> fullNames;
	
	public AmbiguousAuthenticationException(List<String> people){
		super("Multiple users are associated with this email - specify the appropirate user to proceeed with authentication.");
		this.fullNames = people;
	}

	public List<String> getFullNames() {
		return fullNames;
	}
	
	

}
