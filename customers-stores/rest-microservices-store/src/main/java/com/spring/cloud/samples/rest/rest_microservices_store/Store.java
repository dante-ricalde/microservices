package com.spring.cloud.samples.rest.rest_microservices_store;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Store {
	
	private final @Id String id;
	
	private final String name;
	
	private final Address address;
	
	public Store(String name, Address address) {
		this.name = name;
		this.address = address;
		this.id = null;
	}
	
	protected Store() {
		this.id = null;
		this.name = null;
		this.address = null;
	}
}
