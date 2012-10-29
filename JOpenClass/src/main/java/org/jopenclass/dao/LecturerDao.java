package org.jopenclass.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author madhumal
 * 
 */

@Repository
public class LecturerDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 
	 * @param lectuer
	 */
	public void saveLecturer(Lecturer lectuer) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(lectuer);
		session.getTransaction().commit();
		session.close();
	}

}
