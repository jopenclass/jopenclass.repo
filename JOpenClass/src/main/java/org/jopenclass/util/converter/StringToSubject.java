package org.jopenclass.util.converter;

import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToSubject implements Converter<String, Subject> {
	@Autowired
	private SubjectDao courseDao;

	@Override
	public Subject convert(String source) {
		return courseDao.getSubjectById(Long.valueOf(source));
	}
}
