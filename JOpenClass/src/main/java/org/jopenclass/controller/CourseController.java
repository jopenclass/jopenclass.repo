package org.jopenclass.controller;

import java.util.List;

import javax.validation.Valid;

import org.jopenclass.dao.CourseDao;
import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Course;
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
public class CourseController {

	@Autowired
	private CourseDao courseDao;
	@Autowired
	private LecturerDao lecturerDao;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/savecourse", method = RequestMethod.GET)
	public String viewAddCourse(final ModelMap model) {
		
		List<Lecturer> lecturers = lecturerDao.getAllLecturers();
		
		if (lecturers.isEmpty() || lecturers.equals(null)) {
			
			model.addAttribute("operation", "Add a new Lecturer");
			model.addAttribute("message",
					"Please add a lecturer before adding a course");
			model.addAttribute("lecturer", new Lecturer());
			return "lecturer/lecturer";
		} else {
			
			model.addAttribute("course", new Course());
			model.addAttribute("lecturers", lecturers);
			model.addAttribute("operation", "Add a new course");

			return "course/course";
		}

	}

	/**
	 * 
	 * @param model
	 * @param course
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/savecourse", method = RequestMethod.POST)
	public String saveCourse(final ModelMap model, @Valid final Course course,
			final BindingResult result) {
		model.addAttribute("lecturers", lecturerDao.getAllLecturers());
		model.addAttribute("operation", "Add a new course");
		if (result.hasErrors()) {

			return "course/course";
		}
		try {

			courseDao.saveCourse(course);
			model.addAttribute("message", "Successfully added the course");
			model.addAttribute("course", new Course());

		} catch (Exception e) {
			System.out.println(e);
			model.addAttribute("message",
					"Something went wrong when adding the course");
		}

		return "course/course";
	}

}
