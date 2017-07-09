package com.mvp.springboot_docker_tomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootDockerTomcatApplication extends SpringBootServletInitializer {
	
	@RequestMapping("/home")
	public String home() {
		return "Spring Boot war deployment in Tomcat Docker Container successfull";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDockerTomcatApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootDockerTomcatApplication.class);
	}
}
