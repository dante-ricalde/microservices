package com.spring.cloud.config.server;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
//import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author dante
 *
 */
@Configuration
@EnableAutoConfiguration
//@EnableDiscoveryClient
@EnableConfigServer
public class ConfigServerApplication {
	
	/** USED in Spring boot (spring-boot-starter-parent) <version>2.0.0.M4</version>
	@Bean
	@ConditionalOnBean(LoggingSystem.class)
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint
	public LoggersEndpoint loggersEndpoint(LoggingSystem loggingSystem) {
		return new LoggersEndpoint(loggingSystem);
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
