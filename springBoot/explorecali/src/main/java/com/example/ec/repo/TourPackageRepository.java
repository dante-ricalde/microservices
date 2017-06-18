package com.example.ec.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.ec.domain.TourPackage;

/**
 * 
 * @author Dante Ricalde
 *
 */
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
	
	
	TourPackage findByName(@Param(value = "name") String name);
}
