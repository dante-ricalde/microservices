package com.redhat.examples.web;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "greeting")
public class GreeterRestController {

	private RestTemplate template = new RestTemplate();

	private String saying;

	private String backendServiceHost;

	private int backendServicePort;

	@PostConstruct
	public void init() {
		saying += " from cluster hola-springboot at host: " + getIp();
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = "text/plain")
	public String greeting() {
		String backendServiceUrl = String.format("http://%s:%d/api/backend?greeting={greeting}", backendServiceHost,
				backendServicePort);
		// System.out.println("Sending to: " + backendServiceUrl);
		BackendDTO response = template.getForObject(backendServiceUrl, BackendDTO.class, saying);
		// return backendServiceUrl;
		return response.getGreeting() + " at host: " + response.getIp();
	}

	@RequestMapping(value = "/greeting-hystrix", method = RequestMethod.GET, produces = "text/plain")
	public String greetingHystrix() {
		BackendCommand backendCommand = new BackendCommand(backendServiceHost, backendServicePort).withSaying(saying).withTemplate(template);
		BackendDTO response = backendCommand.execute();
		return response.getGreeting() + " at host: " + response.getIp();
	}

	public String getSaying() {
		return saying;
	}

	public void setSaying(String saying) {
		this.saying = saying;
	}

	public String getBackendServiceHost() {
		return backendServiceHost;
	}

	public void setBackendServiceHost(String backendServiceHost) {
		this.backendServiceHost = backendServiceHost;
	}

	public int getBackendServicePort() {
		return backendServicePort;
	}

	public void setBackendServicePort(int backendServicePort) {
		this.backendServicePort = backendServicePort;
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
