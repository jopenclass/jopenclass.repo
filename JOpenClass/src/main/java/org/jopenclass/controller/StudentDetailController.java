/**
@author Pathmasri Ambegoda
 */

package org.jopenclass.controller;


import org.jopenclass.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class StudentDetailController {
	
	
	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/saveStudent", method = RequestMethod.GET)
	public String viewAddCourse(ModelMap model) {
		return "student/student";
	}
}
