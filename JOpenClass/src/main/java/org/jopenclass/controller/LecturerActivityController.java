package org.jopenclass.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LecturerActivityController {
	
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_LEC')")
	@RequestMapping(value = "/getlecturerprofile", method = RequestMethod.GET)
	public Object getLecturerProfile() {
		
		return null;
	}

}
