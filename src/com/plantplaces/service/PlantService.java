package com.plantplaces.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.plantplaces.dao.IFileDAO;
import com.plantplaces.dao.IPlantDAO;
import com.plantplaces.dao.ISpecimenDAO;
import com.plantplaces.dto.Photo;
import com.plantplaces.dto.Plant;
import com.plantplaces.dto.Specimen;
import com.sun.faces.facelets.util.Path;

@Named
public class PlantService implements IPlantService {

	@Inject
	private IPlantDAO plantDAO;

	@Inject
	private ISpecimenDAO specimenDAO;

	@Inject
	private IFileDAO fileDAO;

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
	public void save(Specimen specimen) throws Exception {
		specimenDAO.insert(specimen);

	}

	@Override
	public void loadSpecimens(Plant plant) {
		// TODO Auto-generated method stub
		List<Specimen> specimens = specimenDAO.fetchByPlantId(plant.getGuid());
		plant.setSpecimens(specimens);

	}

	@Override
	public void savePhoto(Photo photo, InputStream inputStream) throws IOException {
		String path = "./Downloads/JavaFullStackWeb/MyJavaFullStackEnterpriseWeb";
		path += "/PlantPlaces/WebContent/images";
		File directory = new File(path);
		String uniquesImageName = getUniqueImageName();
		File file = new File(directory, uniquesImageName);
		fileDAO.save(inputStream, file);
		
		photo.setUri(uniquesImageName);
		//the unique image name will be saved in the database.
	}

	private String getUniqueImageName() {
		String imagePrefix = "plantPlaces";
 		String imageSuffix = ".jpg";
 		String middle ="";
 		
 		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
 		middle = sdf.format(new Date());
 		
 		return imagePrefix + middle + imageSuffix;
	}

}
