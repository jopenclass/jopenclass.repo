package org.jopenclass.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jopenclass.dao.LecturerDao;
import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Subject;
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
	private LecturerDao lecturerDao;
	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/savelecturer", method = RequestMethod.GET)
	public String viewAddCourse(ModelMap model) {
		model.addAttribute("lecturer", new Lecturer());
		model.addAttribute("operation", "Add a new Lecturer");
		model.addAttribute("allSubjects", subjectDao.getAllSubjects());
		return "lecturer/lecturer";
	}

	/**
	 * 
	 * @param lecturer
	 * @param result
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */

	@RequestMapping(value = "/savelecturer", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException {

		Map<String, Object> response = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		JsonNode lecturerNode = rootNode.path("lecturer");
		JsonNode subjectIdNode = rootNode.path("selectedSubjects");
		Lecturer lecturer = mapper.readValue(lecturerNode, Lecturer.class);
		String[] subjectIdList = mapper
				.readValue(subjectIdNode, String[].class);
		Set<Subject> selectedSubjects = new HashSet<Subject>();
		if (subjectIdList != null) {
			for (int i = 0; i < subjectIdList.length; i++) {
				selectedSubjects.add(subjectDao.getSubjectById(Long
						.valueOf(subjectIdList[i])));
			}

			lecturer.setSubjectList(selectedSubjects);
		}
		try {
			lecturer.setId(lecturerDao.saveLecturer(lecturer));
			lecturer.setSubjectList(null);
			lecturer.setUser(lecturer.getUser());
			response.put("lecturer", lecturer);
			response.put("message", "successfully saved!!!");
		} catch (Exception e) {
			System.out.println(e);
			response.put("message",
					"Sorry, an error has occured. Could not add the Lecturer to the system.");
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
		model.addAttribute("allSubjects", subjectDao.getAllSubjects());
		return "lecturer/lecturer_list";
	}

	/**
	 * 
	 * @param lec_ids
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
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
		response.put("lecturer", lecturer);
		return response;
	}

}
