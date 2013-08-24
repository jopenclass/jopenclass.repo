package org.jopenclass.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jopenclass.form.Lecturer;
import org.jopenclass.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author madhumal
 * 
 */
@Controller
public class LecturerDetailController {

	@Autowired
	private LecturerService lecturerService;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/savelecturer", method = RequestMethod.GET)
	public String viewAddCourse(ModelMap model) {
		//lecturerService.getSaveLecturer(model);
		return "lecturer/lecturer";
	}

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/savelecturer", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException {
		return lecturerService.postSaveLecturer(json);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getlecturerlist", method = RequestMethod.GET)
	public String getLecturerList(ModelMap model) {
		lecturerService.getLecturerList(model);
		return "lecturer/lecturerList";
	}

	/**
	 * 
	 * @param lec_ids
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deletelecturers", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteLecturers(@RequestBody Long[] lec_ids) {

		return lecturerService.deleteLecturers(lec_ids);
	}

	/**
	 * 
	 * @param lecturer
	 * @return
	 */
	@RequestMapping(value = "/getlecturerbyid", method = RequestMethod.POST)
	public @ResponseBody
	Object getLecturer(@ModelAttribute(value = "id") Lecturer lecturer) {
		return lecturerService.getLecturerById(lecturer);
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_LEC')")
	@RequestMapping(value = "/getlecturerprofile", method = RequestMethod.GET)
	public String getLecturerProfile(ModelMap model) {

		return lecturerService.getLecturerProfile(model);
	}

	/**
	 * 
	 * @param lecturer
	 * @return
	 */
	@RequestMapping(value = "/loggedlecturerinfo", method = RequestMethod.POST)
	public @ResponseBody
	Object getLoggedLecturerDetails() {
		return lecturerService.getLoggedLecturerDetails();
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
	@RequestMapping(value = "/saveloggedlecinfo", method = RequestMethod.POST)
	public @ResponseBody
	Object updateLoggedLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException, NoSuchAlgorithmException {
		return lecturerService.updateLoggedLecturer(json);
	}

}
