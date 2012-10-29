package org.jopenclass.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author madhumal
 *
 */
@Repository
public class CourseDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 
	 * @param course
	 */
	public void saveCourse(Course course) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(course);
		session.getTransaction().commit();
		session.close();
	}

}
