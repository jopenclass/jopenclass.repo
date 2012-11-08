package org.jopenclass.dao;

import java.util.List;

import org.hibernate.Query;
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
	public long saveLecturer(Lecturer lectuer) {
		Long id;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println(lectuer.getId());
		if(lectuer.getId()>=0){
			session.update(lectuer);
			id=lectuer.getId();
		}else{
			id = (Long) session.save(lectuer);
			
		}
		session.getTransaction().commit();
		session.close();
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public List<Lecturer> getAllLecturers() {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Lecturer");
		@SuppressWarnings("unchecked")
		List<Lecturer> allLecturers = query.list();
		session.getTransaction().commit();
		session.close();
		return allLecturers;

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Lecturer getLecturerById(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Lecturer lecturer = (Lecturer) session.get(Lecturer.class, id);
		session.getTransaction().commit();
		session.close();
		return lecturer;
	}

	public void deleteLecturersById(Long[] lec_ids) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from Lecturer where id=:id");
		
		for (Long id : lec_ids) {
			query.setLong("id", id);
			query.executeUpdate();
		}
		session.getTransaction().commit();
		session.close();
	}

}
