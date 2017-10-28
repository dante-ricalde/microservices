package com.spring.cloud.samples.rest.rest_microservices_store;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@Configuration
//@EnableAutoConfiguration // This configures an embedded mongo data base and throws error because it doesn't find the driver
//@EnableMongoRepositories("com.spring.cloud.samples.rest.rest_microservices_store")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan
//@RefreshScope
//@EnableDiscoveryClient
@ConfigurationProperties
public class StoreApp extends RepositoryRestConfigurerAdapter {
	
	@Value("${newValue:}")
    private String role;
	
	@Autowired
	Environment environment;
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Store.class);
	}
	
	@PostConstruct
	public void exposeIds() {
	}
	
	public static void main(String[] args) {
		SpringApplication.run(StoreApp.class, args);
	}
	
	/*@RefreshScope
	@RestController
	public static class SimpleStoresController {
		
		@Autowired
		Environment environment;
		
		@Autowired
		StoreRepository repository;
		
		@RequestMapping("/simple/stores")
		@ResponseBody
		List<Store> getStores() {
//			Page<Store> all = repository.findAll(PageRequest.of(0, 10));
			Page<Store> all = repository.findAll(new PageRequest(0, 10));
			return all.getContent();
		}
		
	}*/
	
	@Configuration
	@Profile("cloud")
	protected static class CloudFoundryConfiguration {
		
		@Bean
		public Cloud cloud() {
			return new CloudFactory().getCloud();
		}
	}
}
