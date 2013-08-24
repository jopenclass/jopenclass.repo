/**
@author Pathmasri Ambegoda
 */

package org.jopenclass.controller;


import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jopenclass.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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
	 */
	@RequestMapping(value = "/savestudent", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException {
		return studentService.postSaveStudent(json);
	}
}
