package org.jopenclass.controller;

import java.security.Principal;

import org.jopenclass.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author madhumal
 * 
 */
@Controller
public class LoginController {
	
	@Autowired
	private BatchService batchService;
	/**
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {

		String name = principal.getName();
		model.addAttribute("username", name);
		batchService.getFeaturedBatchList(model);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "index";

	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {

		return "login";

	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		model.addAttribute("message", "successfully logged out !!!");
		return "login";

	}

}
