package org.jopenclass.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author madhumal
 *
 */
@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 * @param password
	 * @param newPassword
	 * @param response
	 * @throws NoSuchAlgorithmException
	 */
	public void changePassword(String password, String newPassword,
			Map<String, Object> response) throws NoSuchAlgorithmException {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String userName = authentication.getName();
		if (passwordCorrect(password, userName)) {
			updatePassword(newPassword, userName);
			response.put("message", "password change success!!!");
		} else {
			response.put("message", "incorrect password");
		}
	}

	/**
	 * 
	 * @param newPassword
	 * @param userName
	 * @throws NoSuchAlgorithmException 
	 */
	private void updatePassword(String newPassword, String userName) throws NoSuchAlgorithmException {
		
		String hexStr = "";
		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] passwordByte = newPassword.getBytes();
		md.update(passwordByte);
		byte[] digest = md.digest();

		for (int i = 0; i < digest.length; i++) {
			hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16)
					.substring(1);
		}
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("update User set password=:password where email=:userName");
		query.setString("userName", userName);
		query.setString("password", hexStr);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private boolean passwordCorrect(String password, String userName)
			throws NoSuchAlgorithmException {

		boolean passwordCorrect = false;
		String hexStr = "";

		MessageDigest md = MessageDigest.getInstance("SHA1");
		byte[] passwordByte = password.getBytes();
		md.update(passwordByte);
		byte[] digest = md.digest();

		for (int i = 0; i < digest.length; i++) {
			hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16)
					.substring(1);
		}

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from User where email=:userName and password=:password");
		query.setString("userName", userName);
		query.setString("password", hexStr);

		@SuppressWarnings("unchecked")
		List<User> users = (ArrayList<User>) query.list();
		if (users.size() > 0) {
			passwordCorrect = true;
		}
		session.getTransaction().commit();
		session.close();

		return passwordCorrect;
	}

}
