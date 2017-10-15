package com.spring.cloud.samples.rest.rest_microservices_store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository interface for out-of-the-box paginating access to {@link Store}s and a query method to find stores 
 * by location and distance.
 *
 */
public interface StoreRepository extends PagingAndSortingRepository<Store, String> {

	@RestResource(rel = "by-location")
	Page<Store> findByAddressLocationNear(
			@Param("location") Point location, @Param("distance") Distance distance, Pageable pageable);
}
