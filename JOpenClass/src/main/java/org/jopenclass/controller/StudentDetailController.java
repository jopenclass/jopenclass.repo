/**
@author Pathmasri Ambegoda
 */

package org.jopenclass.controller;

import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentDetailController {

	@RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException {				
		return null;
	}
}
