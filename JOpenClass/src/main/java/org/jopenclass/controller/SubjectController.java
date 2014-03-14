package org.jopenclass.controller;

import java.util.List;

import javax.validation.Valid;

import org.jopenclass.form.Subject;
import org.jopenclass.service.SubjectService;
import org.jopenclass.util.converter.CustomUserResponse;
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
		return "subject/subjectList";
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
	
	@RequestMapping(value = "/displayallsubjects", method = RequestMethod.GET)
	public @ResponseBody
	CustomUserResponse DisplayAllSubejcts() {
		
		List<Subject> subjects = subjectService.getAllSubjects();
    	
    	// Initialize our custom user response wrapper
    	CustomUserResponse<Subject> response = new CustomUserResponse<Subject>();
    	
    	// Assign the result from the service to this response
    	response.setRows(subjects);

    	// Assign the total number of records found. This is used for paging
    	response.setRecords(String.valueOf(subjects.size()));
    	
    	// Since our service is just a simple service for teaching purposes
    	// We didn't really do any paging. But normally your DAOs or your persistence layer should support this
    	// Assign a dummy page
    	response.setPage( "1" );
    	
    	// Same. Assign a dummy total pages
    	response.setTotal( "10" );
    	
    	// Return the response
    	// Spring will automatically convert our CustomUserResponse as JSON object. 
    	// This is triggered by the @ResponseBody annotation. 
    	// It knows this because the JqGrid has set the headers to accept JSON format when it made a request
    	// Spring by default uses Jackson to convert the object to JSON
    	return response;
		
	}
	
	

}
