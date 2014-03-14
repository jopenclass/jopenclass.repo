package org.jopenclass.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author madhumal
 * 
 */
@Service
public class SubjectService {
	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 
	 * @param model
	 */
	public void getSaveSubject(ModelMap model) {
		model.addAttribute("subject", new Subject());
		model.addAttribute("operation", "Add a new subject");

	}

	/**
	 * 
	 * @param subject
	 * @param result
	 * @return
	 */
	public Object postSaveSubject(Subject subject, BindingResult result) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<ObjectError> results = result.getAllErrors();
			for (ObjectError objectError : results) {
				System.out.println(objectError.getDefaultMessage());
			}
			response.put("message", "Could not add the subject to the system.");
		} else {
			try {
				subject.setId(subjectDao.saveSubject(subject));
				response.put("subject", subject);
				response.put("message", "successfully saved !!!");

			} catch (Exception e) {
				System.out.println(e);

			}
		}
		return response;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Object getSubjectById(Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "succeess");
		Subject subject = subjectDao.getSubjectById(id);
		response.put("subject", subject);
		return response;
	}

	/**
	 * 
	 * @param model
	 */
	public void getSubjectList(ModelMap model) {
		model.addAttribute("subjectList", subjectDao.getAllSubjects());
	}
	
	
	public List<Subject> getAllSubjects(){
		return subjectDao.getAllSubjects();
	}

	/**
	 * 
	 * @param subjectIds
	 * @return
	 */
	public Object deleteSubjects(Long[] subjectIds) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			List<Long> delList = subjectDao.deleteSubjectsById(subjectIds);
			response.put("delList", delList);
			if (subjectIds.length == delList.size())
				response.put("message", "deletion successfull");
			else
				response.put(
						"message",
						"couldn't delete some subjects since there are lecturers assigned to the subject");
		} catch (Exception e) {
			System.out.println("error is : "+e);
			response.put("message", "deletion was not successfull");
		}
		return response;
	}
}
