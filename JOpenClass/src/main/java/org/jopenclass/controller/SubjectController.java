package org.jopenclass.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author madhumal
 * 
 */
@Controller
public class SubjectController {

	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/savesubject", method = RequestMethod.GET)
	public String getSaveCourseCategory(final ModelMap model) {

		model.addAttribute("subject", new Subject());
		model.addAttribute("operation", "Add a new subject");

		return "course/subject";
	}

	/**
	 * 
	 * @param subject
	 * @param result
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/savesubject", method = RequestMethod.POST)
	public @ResponseBody
	Object postSaveCourseCategory(
			@Valid @ModelAttribute(value = "subject") Subject subject,
			BindingResult result) {
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
	@RequestMapping(value = "/getsubjectbyid", method = RequestMethod.POST)
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	Object getCourseCategory(@RequestParam(value = "id") Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "succeess");
		Subject subject = subjectDao.getSubjectById(id);
		subject.setLecturerList(null);
		subject.setBatchList(null);
		response.put("subject", subject);
		return response;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getsubjectlist", method = RequestMethod.GET)
	public String getCourseCategoryList(ModelMap model) {
		model.addAttribute("subjectList", subjectDao.getAllSubjects());
		return "course/subject_list";
	}

	/**
	 * 
	 * @param subjectIds
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deletesubjects", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteCourseCategories(@RequestBody Long[] subjectIds) {

		Map<String, Object> response = new HashMap<String, Object>();
		try {
			List<Long> delList = subjectDao.deleteSubjectsById(subjectIds);
			response.put("delList", delList);
			if (subjectIds.length == delList.size())
				response.put("message", "deletion successfull");
			else
				response.put("message", "couldn't delete some subjects since there are lecturers assigned to the subject");
		} catch (Exception e) {
			response.put("message", "deletion was not successfull");
		}
		return response;
	}

}
