package com.pomodoro.integration;

import org.springframework.http.ResponseEntity;

import com.pomodoro.integration.manuscript.domain.AuthenticationException;

public interface TimeTracking<T, R> {
	
	public ResponseEntity<R> startTimeTracking(T t);
	
	public ResponseEntity<R> stopTimeTracking(T t);

}
