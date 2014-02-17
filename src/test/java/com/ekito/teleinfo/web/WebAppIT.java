package com.ekito.teleinfo.web;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Integration test. Only works if the actual spring-boot application is
 * started. Note: All integration tests must finish with 'IT' to be properly
 * picked up by maven surefire.
 * 
 */
public class WebAppIT {

	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = getRestTemplate().getForEntity(
				"http://localhost:9000", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response)
					throws IOException {
			}
		});
		return restTemplate;
	}
}