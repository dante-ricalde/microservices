package com.mvpjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootDockerTomcatApplication extends SpringBootServletInitializer {
	
	@Autowired
	MongoService mongoLoggerService;
	
	@RequestMapping("/")
	public String home() {
		mongoLoggerService.logToMongo(new LogRecord("INFO", "New Home page hit"));
		return "Spring Boot war deployment in Tomcat Docker Container successfull <P>" + 
				mongoLoggerService.getMongoDbInfo();
	}
	
	/*
	@RequestMapping("/home")
	public String home() {
		return "Spring Boot war deployment in Tomcat Docker Container successfull";
	}*/
	
	@RequestMapping("/hits")
	public String getHomePageHits() {
		long homePageHitCount = mongoLoggerService.getHomePageHitCount();
		return "The Home page has been hit " + homePageHitCount + " times";
	}
	

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDockerTomcatApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootDockerTomcatApplication.class); // web.xml
	}
}
