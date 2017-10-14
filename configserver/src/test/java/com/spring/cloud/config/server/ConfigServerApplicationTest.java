package com.spring.cloud.config.server;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigServerApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class ConfigServerApplicationTest {
	
	@LocalServerPort
	private int port = 0;
    
	@Test
	public void configurationAvailable() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/app/cloud", Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	
	@Test
	public void envPostAvailable() {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate().postForEntity("http://localhost:" + port + "/admin/env", form, Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
}
