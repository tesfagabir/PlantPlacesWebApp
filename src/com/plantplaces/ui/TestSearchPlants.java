package com.plantplaces.ui;

import org.junit.Test;

import com.plantplaces.dto.Plant;

import junit.framework.TestCase;

public class TestSearchPlants extends TestCase {

	private SearchPlants searchPlants;
	private String execute;

	@Test
	public void testSearchPlantsExecute() {
		givenSearchPlantsCreatedWithRedbud();
		whenInvokeExecute();
		thenVerifyReturnSearch();
	}
	
	@Test
	public void testSearchPlantsWithNoRedbud(){
		givenSearchPlantsCreatedWithoutRedbud();
		whenInvokeExecute();
		thenVerifyReturnNoResults();
	}
	
	@Test
	public void testSearchPlantsWithNullPlant(){
		givenSearchPlantsCreatedWithNullPlant();
		whenInvokeExecute();
		thenVerifyReturnNoResults();
	}

	private void givenSearchPlantsCreatedWithNullPlant() {
		searchPlants = new SearchPlants();
	}

	private void givenSearchPlantsCreatedWithoutRedbud() {
		searchPlants = new SearchPlants();
		Plant plant = new Plant();
		plant.setName("Pawpaw");
		searchPlants.setPlant(plant);
	}

	private void thenVerifyReturnNoResults() {
		assertEquals("noresults", execute);
	}

	private void givenSearchPlantsCreatedWithRedbud() {
		searchPlants = new SearchPlants();
		Plant plant = new Plant();
		plant.setName("Redbud");
		searchPlants.setPlant(plant);

	}

	private void whenInvokeExecute() {
		execute = searchPlants.execute();
	}

	private void thenVerifyReturnSearch() {
		assertEquals("search", execute);
	}

}
