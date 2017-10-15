package com.spring.cloud.samples.rest.rest_microservices_store;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import lombok.Value;

@Value
public class Address {
	
	private final String street, city, zip;
	
	private final @GeoSpatialIndexed Point location;

}
