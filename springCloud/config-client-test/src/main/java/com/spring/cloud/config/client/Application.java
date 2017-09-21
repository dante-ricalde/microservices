package com.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author dante
 *
 */
@Configuration
@EnableAutoConfiguration
@RestController
public class Application {
	
	@Value(value = "${config.name:'kk'}")
//	@Value(value = "${USE_KUBERNETES_DISCOVERY:'false'}")
	String name = "world";
	
	@RequestMapping("/")
	public String home() {
		return "Hello " + name;
	}
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		Thread.sleep(50000);
	}
}
