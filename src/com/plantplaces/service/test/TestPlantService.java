package com.plantplaces.service.test;

import org.junit.Test;

import junit.framework.TestCase;

public class TestPlantService extends TestCase{
	
	@Test
	public void testFilterPlants(){
		givenThatPlantServiceIsPopulatedWithPlantDAO();
		whenFilterWithRed();
		thenFilterTwoResults();
	}

	private void thenFilterTwoResults() {
		// TODO Auto-generated method stub
		
	}

	private void whenFilterWithRed() {
		// TODO Auto-generated method stub
		
	}

	private void givenThatPlantServiceIsPopulatedWithPlantDAO() {
		// TODO Auto-generated method stub
		
	}

}
