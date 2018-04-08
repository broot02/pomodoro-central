package com.pomodoro.integration.manuscript.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeTrackingRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4453084889040880323L;
	
	private String ixBug;
	private String token;
	private String baseUrl;
	
	
	@JsonIgnore
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getIxBug() {
		return ixBug;
	}

	public void setIxBug(String ixBug) {
		this.ixBug = ixBug;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
