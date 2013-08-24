package org.jopenclass.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jopenclass.dao.LecturerDao;
import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Subject;
import org.jopenclass.form.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class LecturerService {
	@Autowired
	private LecturerDao lecturerDao;
	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 
	 * @param model
	 */
	public void getSaveLecturer(ModelMap model) {
		model.addAttribute("lecturer", new Lecturer());
		model.addAttribute("operation", "Add a new Lecturer");
		model.addAttribute("allSubjects", subjectDao.getAllSubjects());

	}

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public Object postSaveLecturer(String json) throws JsonProcessingException,
			IOException {
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
			//lecturer.setId(lecturerDao.saveLecturer(lecturer));
			//lecturer.setUser(lecturer.getUser());
			lecturerDao.saveLecturer(lecturer);
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
	 */
	public void getLecturerList(ModelMap model) {
		model.addAttribute("lecturerList", lecturerDao.getAllLecturers());
		model.addAttribute("operation", "Add a new Lecturer");
		model.addAttribute("allSubjects", subjectDao.getAllSubjects());

	}

	/**
	 * 
	 * @param lecturer
	 * @return
	 */
	public Object getLecturerById(Lecturer lecturer) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", "succeess");
		response.put("lecturer", lecturer);
		return response;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public String getLecturerProfile(ModelMap model) {
		Lecturer lecturer = lecturerDao.getLoggedInLecturer();
		if (lecturer != null) {
			model.addAttribute("lecturer", lecturer);
			return "lecturer/lecturerPage";
		}
		return null;
	}

	/**
	 * 
	 * @param lec_ids
	 * @return
	 */
	public Object deleteLecturers(Long[] lec_ids) {
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
	 * @return
	 */
	public Object getLoggedLecturerDetails() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("lecturer", lecturerDao.getLoggedInLecturer());
		return response;
	}

	/**
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws NoSuchAlgorithmException 
	 */
	public Object updateLoggedLecturer(String json)
			throws JsonProcessingException, IOException, NoSuchAlgorithmException {

		Map<String, Object> response = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		JsonNode lecturerNode = rootNode.path("lecturer");
		JsonNode userNode = rootNode.path("user");

		Lecturer lecturer = mapper.readValue(lecturerNode, Lecturer.class);
		User user = mapper.readValue(userNode, User.class);

		return lecturerDao.updateLoggedLecturer(lecturer, user, response);
	}
}
