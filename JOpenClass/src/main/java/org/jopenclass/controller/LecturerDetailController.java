package org.jopenclass.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	private LecturerDao lecturerDao;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/savelecturer", method = RequestMethod.GET)
	public String viewAddCourse(ModelMap model) {
		model.addAttribute("lecturer", new Lecturer());
		model.addAttribute("operation", "Add a new Lecturer");
		return "lecturer/lecturer";
	}

	/**
	 * 
	 * @param lecturer
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/savelecturer", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(
			@Valid @ModelAttribute(value = "lecturer") Lecturer lecturer,
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<ObjectError> results = result.getAllErrors();
			for (ObjectError objectError : results) {
				System.out.println(objectError.getDefaultMessage());
			}
			response.put("message", "Could not add the Lecturer to the system.");
		} else {
			try {
				lecturer.setId(lecturerDao.saveLecturer(lecturer));
				response.put("lecturer", lecturer);
				response.put("message", "successfully saved!!!");
			} catch (Exception e) {
				System.out.println(e);
				response.put("message",
						"Sorry, an error has occured. Could not add the Lecturer to the system.");
			}
		}

		return response;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getlecturerlist", method = RequestMethod.GET)
	public String getLecturerList(ModelMap model) {
		model.addAttribute("lecturerList", lecturerDao.getAllLecturers());
		model.addAttribute("operation", "Add a new Lecturer");
		return "lecturer/lecturer_list";
	}

	/**
	 * 
	 * @param lec_ids
	 * @return
	 */
	@RequestMapping(value = "/deletelecturers", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteLecturers(@RequestBody Long[] lec_ids) {

		Map<String, Object> response = new HashMap<String, Object>();
		try {
			lecturerDao.deleteLecturersById(lec_ids);
			response.put("message", "deletion successfull");
		} catch (Exception e) {
			response.put("message", "deletion was not successfull");
		}
		return response;
	}

	/**
	 * 
	 * @param lecturer
	 * @return
	 */
	@RequestMapping(value = "/getlecturerbyid", method = RequestMethod.POST)
	public @ResponseBody
	Object getLecturer(@ModelAttribute(value = "id") Lecturer lecturer) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "succeess");
		// to avoid lazy initialization exception
		lecturer.setCourseList(null);
		response.put("lecturer", lecturer);
		return response;
	}

}
