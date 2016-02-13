package com.plantplaces.service;

import java.util.List;

import com.plantplaces.dto.Plant;

/**
 * IPlantService includes all business related functions for a Plant and related
 * entities
 * 
 * @author Tesfagabir Meharizghi
 *
 */
public interface IPlantService {

	/**
	 * Returns a collection of plant objects that contain a filter text
	 * 
	 * @param filter
	 *            a substring that should be contained in a returned plants
	 * @return a collection of matching plants
	 */
	public List<Plant> filterPlants(String filter);

	/**
	 * Save the plant and perform a validation check.
	 * 
	 * @param plant
	 *            the plant we are persisting
	 * @throws Exception
	 *             if unable to save
	 */
	void save(Plant plant) throws Exception;
}
