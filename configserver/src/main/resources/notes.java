I have coded ConfigServerApplication and application.yml
I need to continue configuring the file bootstrap.yml, git.properties and the keystore.jks, Dockerfile
and the class ApplicationTests, docker-compose and then config clients to connect and test using rabbit mq


Observations:
	I have noticed that when I increased the version of spring boot to 2.0.0.M4
	all the configuration of actuator has changed, including its security..
	There is a new endpoint called
	
	@Bean
	@ConditionalOnBean(LoggingSystem.class)
	@ConditionalOnMissingBean
	@ConditionalOnEnabledEndpoint
	public LoggersEndpoint loggersEndpoint(LoggingSystem loggingSystem) {
		return new LoggersEndpoint(loggingSystem);
	}
	
	which is necessary to configure
	
	For more information read this article:
		https://spring.io/blog/2017/08/22/introducing-actuator-endpoints-in-spring-boot-2-0
	
	For an issue of time I have avoid this version of spring boot 2.0.0.M4 to avoid configuring all these things, instead I have used
	spring boot 2.0.0.M2 (until this version spring actuator has not suffered changes)