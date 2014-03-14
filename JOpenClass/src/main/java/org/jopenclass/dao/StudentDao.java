/**
@author Pathmasri Ambegoda
*/

package org.jopenclass.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.Batch;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	/**
	 * returned only the profile information related to the current lecturer.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Student getLoggedInStudent() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String userName = authentication.getName();
		Set<Batch> batchList = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from Student where email=:userName");
		query.setString("userName", userName);

		List<Student> students = (ArrayList<Student>) query.list();

		Student student = students.get(0);

		session.getTransaction().commit();

		session.close();

		if (students.size() > 0)
			return student;

		return null;
	}
	
	
	/**
	 * 
	 * @param lecturer
	 * @param user
	 * @param response
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public Object updateLoggedStudent(Student student,
			Map<String, Object> response) throws NoSuchAlgorithmException {

		Long loggedStudentId = getLoggedInStudent().getUserId();
		String email = student.getEmail();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		String contactNumber = student.getContactNumber();
		String address = student.getAddress();
		boolean emailChanged=false;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		System.out.println(">>>>>>>>>>>>>>>>>"+email);
		
		Student stud = (Student) session.get(Student.class, loggedStudentId);
		stud.setFirstName(firstName);
		stud.setLastName(lastName);
		stud.setContactNumber(contactNumber);
		
		if(!email.equals(stud.getEmail())){
			emailChanged = true;
			System.out.println(email);
			System.out.println(stud.getEmail());
		}
		
		stud.setEmail(email);
		stud.setAddress(address);
		
		//SecurityContextHolder.getContext().setAuthentication(authentication)
		session.merge(stud);
		session.getTransaction().commit();
		session.close();
		if(emailChanged)
			response.put("emailChanged", "yes");
		response.put("message", "success");
		return response;

	}
	
}
