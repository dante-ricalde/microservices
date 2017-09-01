package com.redhat.examples.web;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;

import io.fabric8.kubeflix.ribbon.KubernetesServerList;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "greeting")
public class GreeterRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(GreeterRestController.class);

	private RestTemplate template = new RestTemplate();

	private String saying;

	private String backendServiceHost;

	private int backendServicePort;
	
	@Value(value = "${USE_KUBERNETES_DISCOVERY:'false'}")
	private String useKubernetesDiscovery;
	
	private ILoadBalancer loadBalancer;
	private IClientConfig config;
	
//	@Value("${server.port}")
//    private String serverPort;

	@PostConstruct
	public void init() {
		saying += " from cluster hola-springboot at host: " + getIp();
		System.out.println("Value of USE_KUBERNETES_DISCOVERY: " + useKubernetesDiscovery);
		this.config = new DefaultClientConfigImpl();
		this.config.loadProperties("backend");
		if (Boolean.valueOf(useKubernetesDiscovery)) {
			logger.debug("Using Kubernetes discovery for ribbon ...");
			loadBalancer = LoadBalancerBuilder.newBuilder().withDynamicServerList(new KubernetesServerList(config)).buildDynamicServerListLoadBalancer();
		}
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
	
	@GetMapping(value = "/greeting-ribbon", produces = "text/plain")
	public String greetingRibbon() {
		return null;
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

	public String getUseKubernetesDiscovery() {
		return useKubernetesDiscovery;
	}

	public void setUseKubernetesDiscovery(String useKubernetesDiscovery) {
		this.useKubernetesDiscovery = useKubernetesDiscovery;
	}

}
