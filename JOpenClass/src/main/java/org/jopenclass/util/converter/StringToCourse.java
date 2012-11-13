package org.jopenclass.util.converter;

import org.jopenclass.dao.CourseDao;
import org.jopenclass.form.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToCourse implements Converter<String, Course> {

	@Autowired
	private CourseDao courseDao;
	
	@Override
	public Course convert(String source) {
		
		return courseDao.getCourseById(Long.valueOf(source));
	}

}
