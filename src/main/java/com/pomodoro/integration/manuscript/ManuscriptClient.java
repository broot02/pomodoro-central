package com.pomodoro.integration.manuscript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pomodoro.integration.TimeTracking;
import com.pomodoro.integration.manuscript.domain.AmbiguousAuthenticationException;
import com.pomodoro.integration.manuscript.domain.AuthenticationException;
import com.pomodoro.integration.manuscript.domain.Case;
import com.pomodoro.integration.manuscript.domain.LogonRequest;
import com.pomodoro.integration.manuscript.domain.ManuscriptApiResponse;
import com.pomodoro.integration.manuscript.domain.TimeTrackingRequest;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Service
public class ManuscriptClient implements TimeTracking<TimeTrackingRequest, ManuscriptApiResponse> {

	private final Logger log = LoggerFactory.getLogger(ManuscriptClient.class);

	/**
	 * Represents error if the authentication was not successful.
	 */
	private static final String LOGIN_FAILURE = "Error 1";
	private static final String LOGIN_MULTIPLE_MATCH = "Error 2";

	private final RestTemplate restTemplate;

	public ManuscriptClient(RestTemplateBuilder builder, ManuscriptResponseErrorHandler handler) {
		this.restTemplate = builder.build();
		this.restTemplate.setErrorHandler(handler);
	}

	/**
	 * Attempts to log the user into the Manuscript API. Requires either email
	 * address/full name, and the password. If successfully logged in a token
	 * will be returned.
	 * 
	 * @param email
	 *            represents either the email address/full name that will be
	 *            utilized to log in this user
	 * @param password
	 *            needed to log the user into the manuscript API.
	 * @param baseUrl
	 *            represents the base url, determines where the API is located.
	 * 
	 * @return the token that is needed to make calls to the Manuscript API.
	 * @throws AuthenticationException
	 *             If the email, and password parameters do not authenticate
	 *             against the API successfully.
	 * @throws AmbiguousAuthenticationException
	 *             If the email is associated with multiple accounts.
	 */
	@SuppressWarnings("unchecked")
	public String login(final String email, final String password, final String baseUrl)
			throws AuthenticationException, AmbiguousAuthenticationException {
		log.debug("Attempting to log user into manuscript API");
		Map<String, String> body = new HashMap<>();
		body.put("email", email);
		body.put("password", password);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(body);
		ResponseEntity<ManuscriptApiResponse> response = restTemplate.exchange(baseUrl + "/api/logon", HttpMethod.POST,
				request, ManuscriptApiResponse.class);

		for (com.pomodoro.integration.manuscript.domain.Error error : response.getBody().getErrors()) {
			if (StringUtils.isNotBlank(error.getMessage()) && error.getMessage().contains(LOGIN_FAILURE)) {
				throw new AuthenticationException();
			} else if (StringUtils.isNotBlank(error.getMessage())
					&& error.getMessage().contains(LOGIN_MULTIPLE_MATCH)) {
				throw new AmbiguousAuthenticationException(
						(List<String>) response.getBody().getData().getAdditionalProperties().get("people"));
			}
		}

		return (String) response.getBody().getData().getAdditionalProperties().get("token");

	}

	/**
	 * Determines if the user is logged into the manuscript API. The token is
	 * used to perform this validation.
	 * 
	 * @param token
	 *            represents the authentication of a user for the API.
	 * @param baseUrl
	 *            represents the base url, determines where the API is located.
	 * @return true if the token is valid, false otherwise
	 */
	public boolean isLoggedIn(final String token, final String baseUrl) {
		log.debug("Checking if user is logged into manuscript API");
		LogonRequest logon = new LogonRequest();
		logon.setToken(token);
		HttpEntity<LogonRequest> request = new HttpEntity<>(logon);

		ResponseEntity<ManuscriptApiResponse> response = restTemplate.exchange(baseUrl + "/api/logon", HttpMethod.POST,
				request, ManuscriptApiResponse.class);
		return response.getBody().getErrors().isEmpty();
	}

	/**
	 * Adds an estimate of time(hours) to a given case. This estimate is
	 * required to perform time tracking.
	 * 
	 * @param token
	 *            represents the authentication of a user for the API.
	 * @param id
	 *            represents the case/bug id, estimate will be added to this
	 *            case.
	 * @param hours
	 *            represents the amount of time the user believe this case will
	 *            take.
	 * @param baseUrl
	 *            represents the base url, determines where the API is located.
	 * @return the response from the manuscript API, only returned for
	 *         convenience.
	 * @throws AuthenticationException
	 *             thrown if the user is not logged in/invalid token.
	 */
	public ResponseEntity<ManuscriptApiResponse> provideCaseEstimate(final String token, final String id,
			final Integer hours, final String baseUrl) throws AuthenticationException {
		log.debug("Attempting to provide estimate for case into the manuscript API");
		if (isLoggedIn(token, baseUrl)) {
			Map<String, String> body = new HashMap<>();
			body.put("token", token);
			body.put("ixBug", id);
			body.put("hrsCurrEst", hours.toString());

			HttpEntity<Map<String, String>> request = new HttpEntity<>(body);
			ResponseEntity<ManuscriptApiResponse> response = restTemplate.exchange(
					"https://pomodoro-central.manuscript.com/api/edit", HttpMethod.POST, request,
					ManuscriptApiResponse.class);
			return response;
		} else {
			throw new AuthenticationException();
		}
	}

	/**
	 * Returns a {@link List<Case>} as returned form the manuscript API.
	 * 
	 * @param searchParam
	 *            can represent the case or case title, determines what is being
	 *            searched for. If case is provided only a single result should
	 *            be returned.
	 * @param token
	 *            represents the authentication of a user for the API.
	 * @param baseUrl
	 *            represents the base url, determines where the API is located.
	 * @return A list of cases, these cases would represent both the case title
	 *         and case number.
	 */
	@SuppressWarnings("unchecked")
	public List<Case> searchForCase(final String searchParam, final String token, final String baseUrl) {
		log.debug("Attempting to search for case - manuscript API");
		if (isLoggedIn(token, baseUrl)) {
			List<Case> responseList = new ArrayList<>();
			Map<String, String> body = new HashMap<>();
			body.put("token", token);
			body.put("cols", "sTitle");
			body.put("q", searchParam);

			HttpEntity<Map<String, String>> request = new HttpEntity<>(body);
			ResponseEntity<ManuscriptApiResponse> response = restTemplate.exchange(
					"https://pomodoro-central.manuscript.com/api/search", HttpMethod.POST, request,
					ManuscriptApiResponse.class);

			for (Map<String, Object> caseResult : (List<Map<String, Object>>) response.getBody().getData()
					.getAdditionalProperties().get("cases")) {
				Case result = new Case();
				result.setCaseNumber((Integer) caseResult.get("ixBug"));
				result.setTitle((String) caseResult.get("sTitle"));
				responseList.add(result);
			}

			return responseList;
		} else {
			throw new AuthenticationException();
		}
	}

	@Override
	public ResponseEntity<ManuscriptApiResponse> startTimeTracking(TimeTrackingRequest timeTrackingRequest)
			throws AuthenticationException {
		if (isLoggedIn(timeTrackingRequest.getToken(), timeTrackingRequest.getBaseUrl())) {
			HttpEntity<TimeTrackingRequest> request = new HttpEntity<>(timeTrackingRequest);
			ResponseEntity<ManuscriptApiResponse> response = restTemplate.exchange(
					"https://pomodoro-central.manuscript.com/api/startWork", HttpMethod.POST, request,
					ManuscriptApiResponse.class);
			return response;
		} else {
			throw new AuthenticationException();
		}

	}

	@Override
	public ResponseEntity<ManuscriptApiResponse> stopTimeTracking(TimeTrackingRequest timeTrackingRequest)
			throws AuthenticationException {
		if (isLoggedIn(timeTrackingRequest.getToken(), timeTrackingRequest.getBaseUrl())) {
			HttpEntity<TimeTrackingRequest> request = new HttpEntity<>(timeTrackingRequest);
			ResponseEntity<ManuscriptApiResponse> response = restTemplate.exchange(
					"https://pomodoro-central.manuscript.com/api/stopWork", HttpMethod.POST, request,
					ManuscriptApiResponse.class);
			return response;
		} else {
			throw new AuthenticationException();
		}

	}

}
