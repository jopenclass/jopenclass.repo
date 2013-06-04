package org.jopenclass.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jopenclass.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws NoSuchAlgorithmException
	 */
	public Object changePassword(String json) throws JsonParseException,
			JsonMappingException, IOException, NoSuchAlgorithmException {

		Map<String, Object> response = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(json);
		JsonNode passwordNode = rootNode.path("password");
		JsonNode newPassNode = rootNode.path("newPassword");
		JsonNode passConfirmNode = rootNode.path("newPassConfirm");

		String password = mapper.readValue(passwordNode, String.class);
		String newPassword = mapper.readValue(newPassNode, String.class);
		String newPassConfirm = mapper.readValue(passConfirmNode, String.class);

		if (!newPassword.equals(newPassConfirm)) {
			response.put("message", "password mismatch");
		} else {
			userDao.changePassword(password, newPassword, response);
		}
		return response;
	}

}
