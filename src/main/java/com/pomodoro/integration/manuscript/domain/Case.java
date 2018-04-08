package com.pomodoro.integration.manuscript.domain;

import java.io.Serializable;

public class Case implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer caseNumber;
	private String title;

	public Integer getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(Integer caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
