package org.jopenclass.controller;

import java.util.List;

import org.jopenclass.dao.CourseDao;
import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Course;
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
public class CourseController {

	@Autowired
	private CourseDao courseDao;
	@Autowired
	private LecturerDao lecturerDao;

	@RequestMapping(value = "/viewaddcourse")
	public ModelAndView viewAddCourse() {
		List<Lecturer> lecturers = lecturerDao.getAllLecturers();
		if (lecturers.isEmpty() || lecturers.equals(null)) {
			ModelAndView mav = new ModelAndView("lecturer/lecturer", "command",
					new Lecturer());

			mav.addObject("operation", "Add a new Lecturer");
			mav.addObject("message",
					"Please add a lecturer before adding a course");

			return mav;
		} else {
			ModelAndView mav = new ModelAndView("course/course", "command",
					new Course());

			mav.addObject("lecturers", lecturers);
			mav.addObject("operation", "Add a new course");

			return mav;
		}

	}

	/**
	 * 
	 * @param course
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/savecourse")
	public ModelAndView saveCourse(@ModelAttribute("course") Course course,
			BindingResult result) {
		ModelAndView mav = new ModelAndView("course/course", "command",
				new Course());
		mav.addObject("lecturers", lecturerDao.getAllLecturers());
		mav.addObject("operation", "Add a new course");
		try {

			courseDao.saveCourse(course);
			mav.addObject("message", "Successfully added the course");

		} catch (Exception e) {
			System.out.println(e);
			mav.addObject("message",
					"Something went wrong when adding the course");
		}

		return mav;
	}

}
