/**
@author Pathmasri Ambegoda
*/

package org.jopenclass.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {
	
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
	 * @param student
	 * @throws NoSuchAlgorithmException
	 */
	public long saveStudent(Student student) throws NoSuchAlgorithmException {

		Long id;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
        id = (Long) session.save(student);
		session.getTransaction().commit();
		session.close();

		return id;
	}
	
	
	
}
