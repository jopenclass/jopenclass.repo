package org.jopenclass.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.jopenclass.dao.CourseDao;
import org.jopenclass.dao.LecturerDao;
import org.jopenclass.form.Course;
import org.jopenclass.form.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	public String getSaveCourse(final ModelMap model) {

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
	 * @param course
	 * @param result
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@RequestMapping(value = "/savecourse", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@Valid @ModelAttribute(value = "course") Course course,
			BindingResult result) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<ObjectError> results = result.getAllErrors();
			for (ObjectError objectError : results) {
				System.out.println(objectError.getDefaultMessage());
			}
			response.put("message", "Could not add the Course to the system.");
		} else {
			try {
				course.setId(courseDao.saveCourse(course));
				course.setLecturer(course.getLecturer());
				Course cse = new Course();
				cse.setId(course.getId());
				cse.setCourseName(course.getCourseName());
				cse.setFee(course.getFee());
				Lecturer lec = new Lecturer();
				lec.setId(course.getLecturer().getId());
				lec.setFirstName(course.getLecturer().getFirstName());
				lec.setLastName(course.getLecturer().getLastName());
				cse.setLecturer(lec);
				cse.setGrade(course.getGrade());
				response.put("course", cse);
				response.put("message", "successfully saved !!!");

			} catch (Exception e) {
				System.out.println(e);

			}
		}

		return response;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getcourselist", method = RequestMethod.GET)
	public String getLecturerList(ModelMap model) {
		model.addAttribute("courseList", courseDao.getAllCourses());
		model.addAttribute("lecturerList", lecturerDao.getAllLecturers());
		return "course/course_list";
	}

	@RequestMapping(value = "/deletecourses", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteCourses(@RequestBody Long[] course_ids) {

		Map<String, Object> response = new HashMap<String, Object>();
		try {
			courseDao.deleteCoursesById(course_ids);
			response.put("message", "deletion successfull");
		} catch (Exception e) {
			response.put("message", "deletion was not successfull");
		}
		return response;
	}

	/**
	 * 
	 * @param course
	 * @return
	 */
	@RequestMapping(value = "/getcoursebyid", method = RequestMethod.POST)
	public @ResponseBody
	Object getLecturer(@ModelAttribute(value = "id") Course course) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "succeess");
		Lecturer lec = new Lecturer();
		lec.setFirstName(course.getLecturer().getFirstName());
		lec.setLastName(course.getLecturer().getLastName());
		lec.setId(course.getLecturer().getId());
		Course cse = new Course();
		cse.setLecturer(lec);
		cse.setCourseName(course.getCourseName());
		cse.setFee(course.getFee());
		cse.setGrade(course.getGrade());
		cse.setId(course.getId());
		response.put("course", cse);
		return response;
	}

}
