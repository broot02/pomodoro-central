package com.pomodoro.integration.manuscript;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class ManuscriptResponseErrorHandler implements ResponseErrorHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ManuscriptResponseErrorHandler.class);
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		boolean hasError = false;
		
		if(response.getStatusCode().is5xxServerError()){
			hasError = true;
		} else if(response.getStatusCode().is4xxClientError() && response.getStatusCode() != HttpStatus.BAD_REQUEST) {
			hasError = true;
		}
		return hasError;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
	}

}
