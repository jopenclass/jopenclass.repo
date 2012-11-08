package org.jopenclass.dao;

import java.util.List;

import org.hibernate.Query;
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
	 * @return
	 */
	public long saveCourse(Course course) {

		Long id;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.out.println(course.getId());
		if(course.getId()>=0){
			course.getLecturer();
			session.update(course);
			id=course.getId();
		}else{
			id = (Long) session.save(course);
			System.out.println("skdjfskjhfkajdfkshjkfhjadfs09fdusiejflskejflskjflakjflasdkjflaskjflkjaldfkjslfkjlakdfjalkjflaksjdlfkjsldfkjsldkfjslkfjslkjflskjf");
			
		}
		session.getTransaction().commit();
		session.close();
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public List<Course> getAllCourses() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Course");
		@SuppressWarnings("unchecked")
		List<Course> allCourses = query.list();
		session.getTransaction().commit();
		session.close();
		return allCourses;
	}

	/**
	 * 
	 * @param course_ids
	 */
	public void deleteCoursesById(Long[] course_ids) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("delete from Course where id=:id");
		for (Long id : course_ids) {
			query.setLong("id", id);
			query.executeUpdate();
		}
		session.getTransaction().commit();
		session.close();

	}

	public Course getCourseById(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Course course = (Course) session.get(Course.class, id);
		session.getTransaction().commit();
		session.close();
		return course;
	}

}
