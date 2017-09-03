package com.redhat.examples.web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

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
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;

import io.fabric8.kubeflix.ribbon.KubernetesServerList;
import rx.Observable;

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
		logger.debug("Value of USE_KUBERNETES_DISCOVERY: " + useKubernetesDiscovery);
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
		if (loadBalancer == null) {
			logger.debug("Using a static list for ribbon");
			Server server = new Server(backendServiceHost, backendServicePort);
			loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(Arrays.asList(server));
		}
		logger.debug("Using ribbon for load balancing..");
		BackendDTO backendDto = LoadBalancerCommand.<BackendDTO>builder().withLoadBalancer(loadBalancer).build().submit(new ServerOperation<BackendDTO>() {
			
			@Override
			public Observable<BackendDTO> call(Server server) {
				// TODO Auto-generated method stub
				// I think hystrix shouldn't be used in this point, because in this point is calling directly to the server chosen by the load balancer,
				// namely directly to the pod and the hystrix should be used when calling to the service so that the balance and discovered to be done by
				// kubernetes. Hystrix should be outside of LoadBalancerCommand.<BackendDTO>builder().withLoadBalancer ...
//				BackendCommand backendCommand = new BackendCommand(server.getHost(), server.getPort()).withSaying(saying).withTemplate(template);
//				return Observable.<BackendDTO>just(backendCommand.execute());
				logger.debug("Calling to backend at host: '{}' and port '{}' using ribbon", server.getHost(), server.getPort());
				String backendServiceUrl = String.format("http://%s:%d/api/backend?greeting={greeting}", server.getHost(), server.getPort());
				// System.out.println("Sending to: " + backendServiceUrl);
				return Observable.<BackendDTO>just(template.getForObject(backendServiceUrl, BackendDTO.class, saying));
			}
		}).toBlocking().first();
		return backendDto.getGreeting() + " at host: " + backendDto.getIp();
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
