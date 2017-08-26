package com.redhat.examples.web;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class BackendCommand extends HystrixCommand<BackendDTO> {

	private String host;

	private int port;

	private String saying;

	private RestTemplate template;

	public BackendCommand(String host, int port) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("springboot.backend"))
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter()
						.withCircuitBreakerEnabled(true)
						.withCircuitBreakerRequestVolumeThreshold(5)
						.withMetricsRollingStatisticalWindowInMilliseconds(5000)));
		this.host = host;
		this.port = port;
	}

	public BackendCommand withSaying(String saying) {
		this.saying = saying;
		return this;
	}
	
	public BackendCommand withTemplate(RestTemplate template) {
		this.template = template;
		return this;
	}

	@Override
	protected BackendDTO run() throws Exception {
		String backendServiceUrl = String.format("http://%s:%d/api/backend?greeting={greeting}", host, port);
		System.out.println("Sending to: " + backendServiceUrl);
		return template.getForObject(backendServiceUrl, BackendDTO.class, saying);
	}
	
	@Override
	protected BackendDTO getFallback() {
		// TODO Auto-generated method stub
		BackendDTO rc = new BackendDTO();
		rc.setGreeting("Greeting fallback!");
		rc.setIp("127.0.0.1");
		rc.setTime(System.currentTimeMillis());
		return rc;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSaying() {
		return saying;
	}

	public void setSaying(String saying) {
		this.saying = saying;
	}

}
