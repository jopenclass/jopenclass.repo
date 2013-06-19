package org.jopenclass.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jopenclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author madhumal
 * 
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "/changepass", method = RequestMethod.POST)
	public @ResponseBody
	Object saveLecturer(@RequestBody String json) throws JsonParseException,
			JsonMappingException, IOException, NoSuchAlgorithmException {
		return userService.changePassword(json);
	}

	/**
	 * 
	 * @param image
	 * @return
	 */
	@RequestMapping(value = "/updateProfilePic", method = RequestMethod.POST)
	public String updateProfilePic(
			@RequestParam(value = "profileImage", required = false) MultipartFile image) {

			if (!image.isEmpty()) {
				if (image.getContentType().equals("image/jpeg")) {
					userService.saveImage(image);
				}
			}

		return "redirect:/getlecturerprofile";
	}
}
