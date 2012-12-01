package org.jopenclass.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jopenclass.dao.BatchDao;
import org.jopenclass.dao.LecturerDao;
import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Batch;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class LecturerActivityController {

	@Autowired
	private LecturerDao lecturerDao;
	@Autowired
	private BatchDao batchDao;
	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_LEC')")
	@RequestMapping(value = "/getlecturerprofile", method = RequestMethod.GET)
	public String getLecturerProfile(ModelMap model) {
		Lecturer lecturer = lecturerDao.getLoggedInLecturer();
		if (lecturer != null) {
			model.addAttribute("lecturer", lecturer);
			return "lecturer/lecturer_page";
		}
		return null;
	}

	@PreAuthorize("isAuthenticated() and hasRole('ROLE_LEC')")
	@RequestMapping(value = "/createbatch", method = RequestMethod.POST)
	public @ResponseBody
	Object createBatch(@RequestBody String json)
			throws JsonProcessingException, IOException {
		System.out.println(json);
		Map<String, Object> response = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		JsonNode batchNode = rootNode.path("batch");
		JsonNode subjectNode = rootNode.path("subject");
		Batch batch = mapper.readValue(batchNode, Batch.class);
		Long subjectId = mapper.readValue(subjectNode, Long.class);
		Subject subject = subjectDao.getSubjectById(subjectId);
		batch.setSubject(subject);
		try {
			batch.setId(batchDao.saveBatch(batch));
			batch.setEnrollmentList(null);
			batch.setLecturer(null);
			batch.setBatchScheduleList(null);
			batch.getSubject().setBatchList(null);
			batch.getSubject().setLecturerList(null);
			response.put("batch", batch);
			response.put("message", "successfully saved");

		} catch (Exception e) {
			System.out.println(e);
			response.put("message",
					"something went wrong while creating the batch");
		}
		return response;
	}

	/**
	 * 
	 * @param delIds
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_LEC')")
	@RequestMapping(value = "/deletebatches", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteBatches(@RequestBody Long[] delIds)
			throws JsonProcessingException, IOException {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			List<Long> deledtedIds = batchDao.deleteBatchesByIds(delIds);
			response.put("delList", deledtedIds);
			if (deledtedIds.size() < delIds.length) {
				response.put("message",
						"cannot delete since currently there are enrollments");
			} else {
				response.put("message", "deletion successfull");
			}
		} catch (Exception e) {
			System.out.println(e);
			response.put("message",
					"something went wrong while creating the batch");
		}
		return response;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getbatchbyid", method = RequestMethod.POST)
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	Object getBatchById(@ModelAttribute(value = "id") Long id) {
		Batch batch = batchDao.getBatchById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("batch", batch);
		return response;
	}
}
