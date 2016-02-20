package com.plantplaces.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import com.plantplaces.dao.IFileDAO;
import com.plantplaces.dao.IPhotoDAO;
import com.plantplaces.dao.IPlantDAO;
import com.plantplaces.dao.ISpecimenDAO;
import com.plantplaces.dto.Photo;
import com.plantplaces.dto.Plant;
import com.plantplaces.dto.Specimen;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Named
public class PlantService implements IPlantService {

	@Inject
	private IPlantDAO plantDAO;

	@Inject
	private ISpecimenDAO specimenDAO;

	@Inject
	private IFileDAO fileDAO;

	@Inject
	private IPhotoDAO photoDAO;
	
	@Inject
	private JMSBean jmsBean;
	
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
		plantDAO.save(plant);

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

	public IPhotoDAO getPhotoDAO() {
		return photoDAO;
	}

	public void setPhotoDAO(IPhotoDAO photoDAO) {
		this.photoDAO = photoDAO;
	}

	public JMSBean getJmsBean() {
		return jmsBean;
	}

	public void setJmsBean(JMSBean jmsBean) {
		this.jmsBean = jmsBean;
	}

	@Override
	public List<Plant> fetchPlants(Plant plant) {
		List<Plant> plants = plantDAO.fetchPlants(plant);
		return plants;
	}

	@Override
	public void save(Specimen specimen) throws Exception {
		specimenDAO.save(specimen);

	}

	@Override
	public void loadSpecimens(Plant plant) {
		// TODO Auto-generated method stub
		List<Specimen> specimens = specimenDAO.fetchByPlantId(plant.getGuid());
		plant.setSpecimens(specimens);

	}

	@Override
	public void savePhoto(Photo photo, InputStream inputStream) throws Exception {
		String path1 = "/home/qiime/Downloads/JavaFullStackWeb/MyJavaFullStackEnterpriseWeb";
		path1 += "/PlantPlaces/WebContent/resources/images";
		File directory = new File(path1);
		String uniquesImageName = getUniqueImageName();
		File file = new File(directory, uniquesImageName);
		fileDAO.save(inputStream, file);
		
		jmsBean.submit(file.toString());
		
		String path2 = "/home/qiime/Downloads/JavaFullStackWeb/MyJavaFullStackEnterpriseWeb";
		path2 += "/PlantPlaces/WebContent/resources/thumbnails";
		File thumbnailDirectory = new File(path2);
		File thumbnailFile = new File(thumbnailDirectory, uniquesImageName);
		
		Thumbnails.of(file).size(100, 100).toFile(thumbnailFile);
		 		
		photo.setUri(uniquesImageName);
		//the unique image name will be saved in the database.
		photoDAO.save(photo);
	}

	private String getUniqueImageName() {
		String imagePrefix = "plantPlaces";
 		String imageSuffix = ".jpg";
 		String middle ="";
 		
 		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
 		middle = sdf.format(new Date());
 		
 		return imagePrefix + middle + imageSuffix;
	}
	
	@Override
 	public List<Photo> fetchPhotos(Specimen specimen) {
 		return photoDAO.fetchPhotos(specimen);
 }

}
