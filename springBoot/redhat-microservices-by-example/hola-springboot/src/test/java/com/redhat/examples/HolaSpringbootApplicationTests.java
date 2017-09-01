package com.redhat.examples;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolaSpringbootApplicationTests {
	
	private static final Logger logger = LoggerFactory.getLogger(HolaSpringbootApplicationTests.class);

	@Test
	public void contextLoads() {
	}

	@Test
	public void testBoolean() {
		Boolean value = Boolean.valueOf("kk");
		logger.debug("Boolean value: " + value);
	}
}
