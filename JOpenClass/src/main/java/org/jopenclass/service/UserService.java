package org.jopenclass.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jopenclass.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Value("${profilePics.path}")
	private String userProfileImgPath;

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

	/**
	 * 
	 * @param image
	 */
	public void saveImage(MultipartFile image) {

		try {

			// save the original upload file
			File file = new File(userProfileImgPath
					+ "/"
					+ SecurityContextHolder.getContext().getAuthentication()
							.getName() + ".jpg");

			FileUtils.writeByteArrayToFile(file, image.getBytes());

			// save the thumbnail
			Thumbnails
					.of(userProfileImgPath
							+ "/"
							+ SecurityContextHolder.getContext()
									.getAuthentication().getName() + ".jpg")
					.size(160, 160)
					.toFile(userProfileImgPath
							+ "/"
							+ SecurityContextHolder.getContext()
									.getAuthentication().getName()
							+ "thumb.jpg");

			// delete the original upload file
			File fileTemp = new File(userProfileImgPath
					+ "/"
					+ SecurityContextHolder.getContext().getAuthentication()
							.getName() + ".jpg");
			if (fileTemp.exists()) {
				fileTemp.delete();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean loggedInIsALectuer() {
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : roles) {
			if (grantedAuthority.toString().equals("ROLE_LEC"))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean loggedInIsAStudent() {
		Collection<? extends GrantedAuthority> roles = SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : roles) {
			if (grantedAuthority.toString().equals("ROLE_STUD")){
				return true;
			}
		}
		return false;
	}

}
