package org.jopenclass.controller;

import javax.validation.Valid;

import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author madhumal
 * 
 */
@Controller
public class LecturerController {

	@Autowired
	private LecturerDao lecturerDao;

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
	@RequestMapping(value = "/savelecturer")
	public String addLecturer(ModelMap model, @Valid Lecturer lecturer,
			BindingResult result) {

		model.addAttribute("operation", "Register a new lecturer");

		if (result.hasErrors()) {
			return "lecturer/lecturer";
		}

		try {
			lecturerDao.saveLecturer(lecturer);
			model.addAttribute("message", "Registration successfull");
			model.addAttribute("lecturer", new Lecturer());
		} catch (Exception e) {
			model.addAttribute("message",
					"Something went in the registration process");
		}

		return "lecturer/lecturer";
	}

}
