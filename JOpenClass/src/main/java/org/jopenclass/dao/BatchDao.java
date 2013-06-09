package org.jopenclass.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jopenclass.form.Batch;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Subject;
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
public class BatchDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 * @param batch
	 * @return
	 */
	public long saveBatch(Batch batch) {

		Long id = batch.getId();

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		String userName = authentication.getName();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from Lecturer where user.email=:userName");
		query.setString("userName", userName);
		@SuppressWarnings("unchecked")
		List<Lecturer> lecturers = (ArrayList<Lecturer>) query.list();

		batch.setLecturer(lecturers.get(0));
		batch.setSubject((Subject) session.get(Subject.class, batch
				.getSubject().getId()));
		if (batch.getId() < 0) {
			batch.setIsFeatured(false);
			id = (Long) session.save(batch);
		} else {
			Batch bat = (Batch) session.get(Batch.class, batch.getId());
			bat.setBatchName(batch.getBatchName());
			bat.setCommenceDate(batch.getCommenceDate());
			bat.setFee(batch.getFee());
			bat.setIntake(batch.getIntake());
			bat.setSubject(batch.getSubject());
			bat.setScheduleDiscription(batch.getScheduleDiscription());

			session.merge(bat);
		}
		session.getTransaction().commit();
		session.close();
		return id;
	}

	/**
	 * 
	 * @param batchId
	 * @return
	 */
	public Batch getBatchById(long batchId) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Batch batch = (Batch) session.get(Batch.class, batchId);
		session.getTransaction().commit();
		session.close();
		return batch;
	}

	/**
	 * returns the ids of the deleted objects
	 * 
	 * @param delIdList
	 * @return
	 */
	public List<Long> deleteBatchesByIds(Long[] delIdList) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String userName = authentication.getName();
		List<Long> deletedList = new ArrayList<Long>();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("delete from Batch where id=:id");

		for (Long delId : delIdList) {

			Batch batch = (Batch) session.get(Batch.class, delId);
			// do not allow to delete if there are enrollment in the batch
			if (batch.getEnrollmentList() == null
					|| batch.getEnrollmentList().size() == 0) {
				// a lecturer is authorized to deleted batches that are assigned
				// only to him
				if (batch.getLecturer().getUser().getEmail().equals(userName)) {
					batch.setSubject(null);
					batch.setBatchScheduleList(null);
					batch.setLecturer(null);

					session.update(batch);

					query.setLong("id", delId);
					query.executeUpdate();
					deletedList.add(delId);
				}
			}
		}
		session.getTransaction().commit();
		session.close();
		return deletedList;
	}

	/**
	 * 
	 * @return
	 */
	public List<Batch> getAllBatches() {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Batch");
		@SuppressWarnings("unchecked")
		List<Batch> batchList = query.list();

		session.getTransaction().commit();
		session.close();

		return batchList;
	}

	/**
	 * 
	 * @param featureIds
	 */
	public void featureBatches(Long[] featureIds) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Batch");
		@SuppressWarnings("unchecked")
		List<Batch> batchList = query.list();

		if (batchList.size() > 0) {
			for (Batch batch : batchList) {
				batch.setIsFeatured(false);
				for (int i = 0; i < featureIds.length; i++) {
					if (batch.getId() == featureIds[i]) {
						batch.setIsFeatured(true);

					}
					session.save(batch);
				}
			}
		}

		session.getTransaction().commit();
		session.close();
	}
}
