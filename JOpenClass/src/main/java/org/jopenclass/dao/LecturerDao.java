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
import org.jopenclass.form.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author madhumal
 * 
 */

@Repository
public class LecturerDao {

	private final static int SHAH_LENGTH = 40;

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
	 * @throws NoSuchAlgorithmException
	 */
	public long saveLecturer(Lecturer lectuer) throws NoSuchAlgorithmException {

		Long id = 1L;
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String hexStr = "";

		// if a password is set, then update to sha1 length of sha1 in db is 40
		if (lectuer != null
				&& (lectuer.getPassword() != null)
				&& (lectuer.getPassword().length()) > 0
				&& lectuer.getPassword().length() < SHAH_LENGTH) {

			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] passwordByte = lectuer.getPassword().getBytes();
			md.update(passwordByte);
			byte[] digest = md.digest();

			for (int i = 0; i < digest.length; i++) {
				hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16)
						.substring(1);
			}
			lectuer.setPassword(hexStr);
		}

		if (lectuer.getUserId() >= 0) {
			Lecturer lec = (Lecturer) session.get(Lecturer.class,
					lectuer.getUserId());
			lec.setFirstName(lectuer.getFirstName());
			lec.setLastName(lectuer.getLastName());
			lec.setAddress(lectuer.getAddress());
			lec.setContactNumber(lectuer.getContactNumber());

			// length of sha1 encoded string in db
			if (lectuer.getPassword() != null
					&& lectuer.getPassword().length() < SHAH_LENGTH
					&& lectuer.getPassword().length() > 0) {
				lec.setPassword(hexStr);
			}

			lec.setEmail(lectuer.getEmail());
			lec.setUserRoles(lectuer.getUserRoles());
			lec.setSubjectList(lectuer.getSubjectList());

			if (lectuer.getSubjectList() == null
					|| lectuer.getSubjectList().size() == 0)
				lec.getSubjectList().clear();

			// used merge since hibernate may try to attach both Lecturers with
			// the same id to the session
			session.merge(lec);
			id = lectuer.getUserId();
		} else {
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

	/**
	 * 
	 * @param lec_ids
	 */
	public void deleteLecturersById(Long[] lec_ids) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("delete from Lecturer where userId=:id");

		for (Long id : lec_ids) {

			Lecturer lecturer = (Lecturer) session.get(Lecturer.class, id);
			if (lecturer.getSubjectList() != null) {
				System.out.println("subs not null");
				lecturer.setSubjectList(null);
			}
			if (lecturer.getBatchList() != null) {
				System.out.println("not null");
				lecturer.setBatchList(null);
			}
			session.update(lecturer);

			lecturer.getUserRoles().clear();
			session.update(lecturer);
			query.setLong("id", id);
			query.executeUpdate();
		}
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * returned only the profile information related to the current lecturer.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Lecturer getLoggedInLecturer() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String userName = authentication.getName();
		Set<Batch> batchList = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from Lecturer where email=:userName");
		query.setString("userName", userName);

		List<Lecturer> lecturers = (ArrayList<Lecturer>) query.list();

		if (lecturers.size() > 0) {

			batchList = (Set<Batch>) lecturers.get(0).getBatchList();
		}

		Lecturer lecturer = lecturers.get(0);

		session.getTransaction().commit();

		// setting null after committing since we do not need to lose enrolement
		// and batch schedules and avoid hibernate lazy init dificulties
		if (batchList != null) {
			for (Batch batch : batchList) {
				batch.setEnrollmentList(null);
				batch.setBatchScheduleList(null);
			}
		}

		lecturer.setBatchList(batchList);
		session.close();

		if (lecturers.size() > 0)
			return lecturer;

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
	public Object updateLoggedLecturer(Lecturer lecturer,
			Map<String, Object> response) throws NoSuchAlgorithmException {

		Long loggedLecId = getLoggedInLecturer().getUserId();
		String email = lecturer.getEmail();
		String lecturerInfo = lecturer.getLecturerInfo();
		String firstName = lecturer.getFirstName();
		String lastName = lecturer.getLastName();
		String contactNumber = lecturer.getContactNumber();
		String address = lecturer.getAddress();
		boolean emailChanged=false;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		System.out.println(">>>>>>>>>>>>>>>>>"+email);
		
		Lecturer lec = (Lecturer) session.get(Lecturer.class, loggedLecId);
		lec.setFirstName(firstName);
		lec.setLastName(lastName);
		lec.setContactNumber(contactNumber);
		lec.setLecturerInfo(lecturerInfo);
		
		if(!email.equals(lec.getEmail())){
			emailChanged = true;
			System.out.println(email);
			System.out.println(lec.getEmail());
		}
		
		lec.setEmail(email);
		lec.setAddress(address);
		
		//SecurityContextHolder.getContext().setAuthentication(authentication)
		session.merge(lec);
		session.getTransaction().commit();
		session.close();
		if(emailChanged)
			response.put("emailChanged", "yes");
		response.put("message", "success");
		return response;

	}
}
