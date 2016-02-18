package com.plantplaces.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.plantplaces.dao.IPlantDAO;
import com.plantplaces.dao.ISpecimenDAO;
import com.plantplaces.dto.Plant;
import com.plantplaces.dto.Specimen;

@Named
public class PlantService implements IPlantService {

	@Inject
	private IPlantDAO plantDAO;
	
	@Inject
	private ISpecimenDAO specimenDAO;

	private List<Plant> allPlants;

	@Override
	public List<Plant> filterPlants(String filter) {

		if (allPlants == null) {
			allPlants = getPlantDAO().fetchPlants();
		}

		// collection we are returning
		List<Plant> returnPlants = new ArrayList<Plant>();

		// filter the list
		// Check all possible plants if they match with the filter text
		for (Plant plant : allPlants) {
			if (plant.toString().contains(filter)) {
				// this plant matches the filter criteria and added to the
				// collection of returning plants
				returnPlants.add(plant);
			}
		}

		return returnPlants;
	}

	public void save(Plant plant) throws Exception {
		if (plant.getGenus() == null || plant.getGenus().isEmpty()) {
			throw new Exception("Genus required");
		}
		plantDAO.insert(plant);

	}

	public IPlantDAO getPlantDAO() {
		return plantDAO;
	}

	public void setPlantDAO(IPlantDAO plantDAO) {
		this.plantDAO = plantDAO;
	}

	public ISpecimenDAO getSpecimenDAO() {
		return specimenDAO;
	}

	public void setSpecimenDAO(ISpecimenDAO specimenDAO) {
		this.specimenDAO = specimenDAO;
	}

	@Override
	public List<Plant> fetchPlants(Plant plant) {
		List<Plant> plants = plantDAO.fetchPlants(plant);
		return plants;
	}
	
	@Override
	public void save(Specimen specimen) throws Exception{
		specimenDAO.insert(specimen);
		
	}
	
	

}
