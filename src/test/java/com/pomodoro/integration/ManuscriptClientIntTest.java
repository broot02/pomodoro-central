package com.pomodoro.integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pomodoro.PomodoroApp;
import com.pomodoro.integration.manuscript.ManuscriptClient;
import com.pomodoro.integration.manuscript.domain.AmbiguousAuthenticationException;
import com.pomodoro.integration.manuscript.domain.AuthenticationException;
import com.pomodoro.integration.manuscript.domain.Case;
import com.pomodoro.integration.manuscript.domain.ManuscriptApiResponse;
import com.pomodoro.integration.manuscript.domain.TimeTrackingRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PomodoroApp.class)
@EnableConfigurationProperties
public class ManuscriptClientIntTest {

	@Autowired
	ManuscriptClient client;

	private final String baseUrl = "https://pomodoro-central.manuscript.com";
	private final String password = "Password123";
	private final String token = "r1h4or309so1bp21676cn93gc6a3p6";
	private final String email = "pomodorouser@gmail.com";
	private final String username = "pomodoro-user";

	private TimeTrackingRequest request;

	@Before
	public void initialize() {
		request = new TimeTrackingRequest();
		request.setIxBug("1");
		request.setToken(token);
		request.setBaseUrl(baseUrl);
	}

	@After
	public void cleanup() {
		client.stopTimeTracking(request);
	}

	@Test
	public void testLogon() {
		String token = null;
		token = client.login(username, password, baseUrl);
		assertNotNull("Token should not be null", token);
	}

	@Test(expected = AuthenticationException.class)
	public void testLogon_InvalidCredentials() {
		String token = null;
		token = client.login(username, "Password", baseUrl);
		assertNull("Token should be null", token);
	}

	@Test(expected = AmbiguousAuthenticationException.class)
	public void testLogon_AmbiguousUser() {
		String token = null;
		token = client.login(email, password, baseUrl);
		assertNull("Token should be null", token);
	}

	@Test
	public void testIsLoggedIn() {
		boolean response = client.isLoggedIn(token, baseUrl);
		assertTrue("Token should be valid", response);
	}

	@Test
	public void testIsNotLoggedIn() {
		boolean response = client.isLoggedIn("r1h4or309so1bp21676cn93gc6a3p213421", baseUrl);
		assertFalse("Token shouldn't be valid", response);
	}

	@Test
	public void testStartTimeTracking() {
		ResponseEntity<ManuscriptApiResponse> response = client.startTimeTracking(request);
		assertNotNull("Response should not be null", response);
		assertEquals("Status should be 200", HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testStopTimeTracking() {
		ResponseEntity<ManuscriptApiResponse> response = client.stopTimeTracking(request);
		assertNotNull("Response should not be null", response);
		assertEquals("Status should be 200", HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testProvideCaseEstimate() {
		ResponseEntity<ManuscriptApiResponse> response = client.provideCaseEstimate(token, "1", 4, baseUrl);
		assertNotNull("Response should not be null", response);
		assertEquals("Status should be 200", HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testSearch_CaseNumber() {
		List<Case> response = client.searchForCase("1", token, baseUrl);
		assertFalse("List should not be empty", response.isEmpty());

	}

	@Test
	public void testSearch_CaseTitle() {
		List<Case> response = client.searchForCase("Welcome", token, baseUrl);
		assertFalse("List should not be empty", response.isEmpty());

	}

}
