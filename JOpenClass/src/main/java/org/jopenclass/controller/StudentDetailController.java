/**
@author Pathmasri Ambegoda
 */

package org.jopenclass.controller;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jopenclass.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class StudentDetailController {
	
	
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/saveStudent", method = RequestMethod.GET)
	public String saveStudent(ModelMap model) {
		return "student/student";
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException, NoSuchAlgorithmException {
		return studentService.postSaveStudent(json);
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_STUD')")
	@RequestMapping(value = "/getstudentprofile", method = RequestMethod.GET)
	public String getStudentProfile(ModelMap model) {

		return studentService.getStudentProfile(model);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "/saveloggedstudentinfo", method = RequestMethod.POST)
	public @ResponseBody
	Object updateLoggedStudent(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException, NoSuchAlgorithmException {
		return studentService.updateLoggedStudent(json);
	}
	
	/**
	 * 
	 * @param lecturer
	 * @return
	 */
	@RequestMapping(value = "/getloggedstudentinfo", method = RequestMethod.POST)
	public @ResponseBody
	Object getLoggedStudentDetails() {
		return studentService.getLoggedStudentDetails();
	}
}
