package org.jopenclass.util.converter;

import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author madhumal
 * 
 */
public class StringToLecturer implements Converter<String, Lecturer> {

	@Autowired
	private LecturerDao lecturerDao;

	@Override
	public Lecturer convert(String source) {
		return lecturerDao.getLecturerById(Long.valueOf(source));
	}

}
