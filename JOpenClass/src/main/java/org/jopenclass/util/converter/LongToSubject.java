package org.jopenclass.util.converter;

import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class LongToSubject implements Converter<Long, Subject> {
	@Autowired
	private SubjectDao courseDao;

	@Override
	public Subject convert(Long source) {
		return courseDao.getSubjectById(source);
	}

}
