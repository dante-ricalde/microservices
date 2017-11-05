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

NOTIFICATIONS AND SPRING CLOUD BUS: WEBHOOK

I have configured the webhook in my config-repo git hub repository with the Payload URL:
	http://c707c0df.ngrok.io/monitor

previously I had to expose my localhost to the internet by doing:
	
	dante@dante-HP-Pavilion-Notebook:~$ ./ngrok http 8888
	the result was: 
		Forwarding    http://c707c0df.ngrok.io -> 127.0.0.1:8888

ENCRYPTION AND DECRYPTION
	KEY MANAGEMENT
		to create a key store for testing:
			~/microservices/configserver/src/main/resources$ keytool -genkeypair -alias mytestkey -keyalg RSA   -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US"   -keypass changeme -keystore server.jks -storepass letmein
	to active the /decrypt and /encrypt endpoints 
		configure the following properties in bootstrap.xml:
			encrypt:
				  keyStore:
	to encrypt a password:
		curl localhost:8888/encrypt -d dbuser -u user:8d9f7ae8-a943-46f7-8398-a6feefffbdb8
		the password 8d9f7ae8-a943-46f7-8398-a6feefffbdb8 appears on the logs once the dependency spring-boot-starter-security
		is added to the pom
    to decrypt a PEM-encoded password:
    	curl localhost:8888/decrypt -d "AQAiwRO2O1Lr6zs+3PU7eY8ZAEGOb7zcrEAhy9F4RDFIz0T8I1d+etw6r0I4IhS3BXlUN21fb56UteXT4PsEbQXF7+JT613fMvgZXUowyLG/tDMdiQ2kTK/p9cbFmdmzU7mBPZwcxQOPEC/Fx2ztCDMh2XyJR3k6JCYDD/3G0M/qXaUrAUHcS1TTJkKYSXJ3ucgUl/Pxn1zJDvJ656sJLj/49RNplqy/sfCWARAgTYKoZWP0NiuLoerXyGA6oBIrAkJw+bOVUi4pODZYEANZhydwNKmLQfFbNHU+wEjsjtX2ymFptxt7QGVpaHX3H0sGMnwEvppfMYSN2Au2gzyPOpuE29aKbxtDHZM3LqYdDA2Lp9vMKaN5W1Td74ufu2Il+xA=" -u user:8d9f7ae8-a943-46f7-8398-a6feefffbdb8
    When you enter to a config file http://localhost:8888/application-default.yml in config server
    by default the passwords are descrypted by the config server
	    spring:
	    	  datasourcekk:
	    	    password: dbuser
	    	    username: dbuser
	If you want the clients to decrypt the configuration locally, instead of doing it in the server and
    If you want that config server to serve encrypted properties you can witch off the decryption of outgoing properties using
    spring.cloud.config.server.encrypt.enabled=false (it should go in bootstrap) and you you can still have /encrypt and /decrypt endpoints (if you provide the encrypt.* configuration to locate a key)
    	If you don’t care about the endpoints, then it should work if you configure neither the key nor the enabled flag.
    
    The next actions are to decrypt the passwords in the client, I am getting the following error when I start the client
    java.lang.IllegalStateException: Cannot decrypt: key=spring.datasourcekk.password  (0%)
    
    I need to deactive the HTTP basic config server autenthication when spring security was added to avoid enter
    the user and password in the browser when I try to get a config file of config server (http://localhost:8888/application-default.yml)
    and to avoid configure these properties in the clients for spring.cloud.config
    	username: user
        password: e0ca601e-d754-4aea-a0d0-ebb4a75ef388
    	