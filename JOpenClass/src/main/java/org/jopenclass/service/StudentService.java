/**
@author Pathmasri Ambegoda
 */

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
import org.jopenclass.dao.StudentDao;
import org.jopenclass.form.Lecturer;
import org.jopenclass.form.Student;
import org.jopenclass.form.Subject;
import org.jopenclass.util.converter.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentDao;

	public Object postSaveStudent(String json) throws JsonProcessingException,
			IOException, NoSuchAlgorithmException {
		Map<String, Object> response = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		//JsonNode studentNode = rootNode.path("student");
		Student student = mapper.readValue(rootNode, Student.class);
		String encryptedPass = PasswordUtil.getEncryptedPassword(student.getPassword());
		student.setPassword(encryptedPass);
	
		try {
			studentDao.saveStudent(student);
			response.put("student", student);
			response.put("message", "successfully saved!!!");
		} catch (Exception e) {
			System.out.println(e);
			response.put("message", "Sorry, an error has occured. Could not add the Student to the system.");
		}
		return response;
	}

}
