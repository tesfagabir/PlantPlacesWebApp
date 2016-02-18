package com.plantplaces.dao;

import javax.inject.Named;

import org.hibernate.Session;

import com.plantplaces.dto.Specimen;

/**
 * A Hibernate persistence implementation of our specimen DAO.
 * @author Tesfagabir Meharizghi
 *
 */
@Named
public class SpecimenHbmDAO implements ISpecimenDAO {
	
	/* (non-Javadoc)
	 * @see com.plantplaces.dao.ISpecimenDAO#insert(com.plantplaces.dto.Specimen)
	 */
	@Override
	public void insert(Specimen specimen) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		session.beginTransaction();
		
		session.save(specimen);
		
		session.getTransaction().commit();
	}

}
