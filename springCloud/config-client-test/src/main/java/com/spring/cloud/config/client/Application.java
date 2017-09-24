package com.spring.cloud.config.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.config.environment.Environment;
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
	
	@Autowired
	private org.springframework.core.env.Environment environment;
	
	@Autowired
	ConfigServicePropertySourceLocator configService;
	
	@RequestMapping("/")
	public String home() {
		System.out.println(configService.toString());
		String actuallyConfigName = environment.getProperty("config.name");
		System.out.println("actuallyConfigName" + actuallyConfigName);
		return "Hello " + name + " at host: " + getIp();
	}
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		Thread.sleep(50000);
	}
	
	private String getIp() {
		String hostname = null;
		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "unknown";
		}
		return hostname;
	}
}
