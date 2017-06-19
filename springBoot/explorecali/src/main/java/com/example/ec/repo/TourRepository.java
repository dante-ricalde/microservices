package com.example.ec.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.ec.domain.Tour;

/**
 * 
 * @author Dante Ricalde
 *
 */
public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {
	
	//List<Tour> findByTourPackageCode(@Param("code") String code);
	
	Page<Tour> findByTourPackageCode(@Param("code") String code, Pageable pageable);

	@Override
	@RestResource(exported=false)
	<S extends Tour> S save(S entity);

	@Override
	@RestResource(exported=false)
	<S extends Tour> Iterable<S> save(Iterable<S> entities);

	@Override
	@RestResource(exported=false)
	void delete(Integer id);

	@Override
	@RestResource(exported=false)
	void delete(Tour entity);

	@Override
	@RestResource(exported=false)
	void delete(Iterable<? extends Tour> entities);

	@Override
	@RestResource(exported=false)
	void deleteAll();
}
