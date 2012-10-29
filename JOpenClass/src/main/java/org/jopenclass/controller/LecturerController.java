package org.jopenclass.controller;

import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
 * @author madhumal
 *
 */
@Controller
public class LecturerController {
	
	@Autowired
	private LecturerDao lecturerDao;
	
	@RequestMapping(value = "/viewaddlecturer")
	public ModelAndView viewAddCourse() {
		ModelAndView mav = new ModelAndView("lecturer/lecturer", "command",
				new Lecturer());
		mav.addObject("operation", "Add a new Lecturer");
		return mav;
	}

	/**
	 * 
	 * @param lecturer
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/savelecturer")
	public ModelAndView addLecturer(
			@ModelAttribute("lecturer") Lecturer lecturer, BindingResult result) {

		ModelAndView mav = new ModelAndView("lecturer/lecturer", "command",
				new Lecturer());
		mav.addObject("operation", "Register a new lecturer");
		try {
			lecturerDao.saveLecturer(lecturer);
			mav.addObject("message", "Registration successfull");
		} catch (Exception e) {
			mav.addObject("message", "Something went in the registration process");
		}
		

		return mav;
	}

}
