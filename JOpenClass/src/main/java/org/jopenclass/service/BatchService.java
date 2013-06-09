package org.jopenclass.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jopenclass.dao.BatchDao;
import org.jopenclass.dao.SubjectDao;
import org.jopenclass.form.Batch;
import org.jopenclass.form.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * 
 * @author madhumal
 * 
 */
@Service
public class BatchService {

	@Autowired
	private BatchDao batchDao;
	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public Object saveBatch(String json) throws JsonProcessingException,
			IOException {
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
	 */
	public Object deleteBatches(Long[] delIds) {
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
	public Object getBatchById(Long id) {
		Batch batch = batchDao.getBatchById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("batch", batch);
		return response;
	}

	public Object getBatchContent(Long id) {
		return null;
	}

	public void getCourseList(ModelMap model) {
		
	}

}
