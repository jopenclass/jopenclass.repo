package org.jopenclass.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author madhumal
 * 
 */
@Repository
public class SubjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 * @param subject
	 * @return
	 */
	public long saveSubject(Subject subject) {
		Long id;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if (subject.getId() >= 0) {
			session.update(subject);
			id = subject.getId();
		} else {
			id = (Long) session.save(subject);
		}
		session.getTransaction().commit();
		session.close();
		return id;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Subject getSubjectById(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Subject subject = (Subject) session.get(Subject.class, id);
		session.getTransaction().commit();
		session.close();
		return subject;
	}

	/**
	 * 
	 * @return
	 */
	public List<Subject> getAllSubjects() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Subject");
		@SuppressWarnings("unchecked")
		List<Subject> allSubjects = query.list();
		session.getTransaction().commit();
		session.close();
		return allSubjects;
	}

	/**
	 * 
	 * @param subjectIds
	 */
	public List<Long> deleteSubjectsById(Long[] subjectIds) {
		List<Long> delList = new ArrayList<Long>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from Subject where id=:id");
		for (Long id : subjectIds) {
			Subject subject = (Subject) session.get(Subject.class, id);
			
			if (subject.getLecturerList() == null
					|| subject.getLecturerList().size() == 0) {
				
				query.setLong("id", id);
				try {
					query.executeUpdate();
					delList.add(id);
				} catch (Exception e) {
					System.out.println("cannot delete since there are some lectuers assigned to the subject");
				}
				
			}
		}
		session.getTransaction().commit();
		session.close();
		return delList;
	}

}
