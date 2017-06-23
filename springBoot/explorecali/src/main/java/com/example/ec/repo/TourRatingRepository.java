package com.example.ec.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {
	
	/**
	 * Lookup all the TourRatings for a tour.
	 * 
	 * @param tourId is the tour Identifier.
	 * @return a List of any found TourRatings
	 */
	List<TourRating> findByPkTourId(Integer tourId);
	
	Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable);
	
	/**
	 * Lookup a TourRating by the TourId and Customer Id.
	 * @param tourId
	 * @param customerId
	 * @return
	 */
	TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);
}
