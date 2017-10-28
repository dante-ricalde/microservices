package com.spring.cloud.samples.rest.rest_microservices_store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class SimpleController {
	
	@Autowired
	Environment environment;
	
	@Value("${newValue:}")
    private String role;
	
	@RequestMapping("/simple/newValue")
	public String getNewValueFromConfigServer() {
		return environment.getProperty("newValue") + role;
	}

}
