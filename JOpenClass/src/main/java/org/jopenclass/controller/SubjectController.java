package org.jopenclass.controller;

import javax.validation.Valid;

import org.jopenclass.form.Subject;
import org.jopenclass.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
	private SubjectService subjectService;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/savesubject", method = RequestMethod.GET)
	public String getSaveSubject(final ModelMap model) {

		subjectService.getSaveSubject(model);

		return "subject/subject";
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
	Object postSaveSubject(
			@Valid @ModelAttribute(value = "subject") Subject subject,
			BindingResult result) {
		

		return subjectService.postSaveSubject(subject,result);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getsubjectbyid", method = RequestMethod.POST)
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	Object getSubjectById(@RequestParam(value = "id") Long id) {

		return subjectService.getSubjectById(id);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getsubjectlist", method = RequestMethod.GET)
	public String getSubjectList(ModelMap model) {
		subjectService.getSubjectList(model);
		return "subject/subject_list";
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
		return subjectService.deleteSubjects(subjectIds);
	}

}
