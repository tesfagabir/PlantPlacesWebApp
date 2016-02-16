package com.plantplaces.dao;

import java.util.List;

import org.junit.Test;

import com.plantplaces.dto.Plant;

import junit.framework.TestCase;

public class TestPlantHbmDAO extends TestCase {

	private PlantHbmDAO plantHbmDAO;
	private List<Plant> plants;

	@Test
	public void testFetchByCommonName() {
		givenPlantDAOInstantiated();
		whenCommonNameIsRedbud();
		thenVerifyResults();
	}

	private void givenPlantDAOInstantiated() {
		plantHbmDAO = new PlantHbmDAO();
	}

	private void whenCommonNameIsRedbud() {
		Plant plant = new Plant();
		plant.setCommon("Redbud");
		plants = plantHbmDAO.fetchPlants(plant);
	}

	private void thenVerifyResults() {
		assertTrue(plants.size() > 0);
		for (Plant plant : plants) {
			assertEquals("Redbud", plant.getCommon());
		}
	}

}
