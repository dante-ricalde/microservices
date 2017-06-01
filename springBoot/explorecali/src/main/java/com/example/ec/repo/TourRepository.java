package com.example.ec.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.ec.domain.Tour;

/**
 * 
 * @author Dante Ricalde
 *
 */
public interface TourRepository extends CrudRepository<Tour, Integer> {

}
