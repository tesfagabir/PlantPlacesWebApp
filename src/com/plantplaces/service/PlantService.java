package com.plantplaces.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.plantplaces.dao.IPlantDAO;
import com.plantplaces.dto.Plant;

@Named
public class PlantService implements IPlantService {

	@Inject
	private IPlantDAO plantDAO;

	private List<Plant> allPlants;
	
	public IPlantDAO getPlantDAO() {
		return plantDAO;
	}

	public void setPlantDAO(IPlantDAO plantDAO) {
		this.plantDAO = plantDAO;
	}

	@Override
	public List<Plant> filterPlants(String filter) {

		if (allPlants == null) {
			allPlants = plantDAO.fetchPlants();
		}

		// collection we are returning
		List<Plant> returnPlants = new ArrayList<Plant>();

		// filter the list
		// Check all possible plants if they match with the filter text
		for (Plant plant : returnPlants) {
			if (plant.toString().contains(filter)) {
				// this plant matches the filter criteria and added to the
				// collection of returning plants
				returnPlants.add(plant);
			}
		}

		return returnPlants;
	}

}
