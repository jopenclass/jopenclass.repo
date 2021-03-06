package org.jopenclass.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.jopenclass.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author madhumal
 * 
 */

@Controller
public class BatchController {

	@Autowired
	private BatchService batchService;

	/**
	 * 
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_LEC')")
	@RequestMapping(value = "/createbatch", method = RequestMethod.POST)
	public @ResponseBody
	Object createBatch(@RequestBody String json)
			throws JsonProcessingException, IOException {
		return batchService.saveBatch(json);
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

		return batchService.deleteBatches(delIds);
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

		return batchService.getBatchById(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_LEC','ROLE_STUDENT')")
	@RequestMapping(value = "/enterbatch", method = RequestMethod.GET)
	public String enterBatch(@RequestParam(value = "batchId") Long id,ModelMap model) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("batchId", id);
		model.addAttribute("batchObject", batchService.getBatchContent(id));
		mav.setViewName("batch/batch");
		return "batch/batch";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getBatchList", method = RequestMethod.GET)
	public String getBatchList(ModelMap model) {
		batchService.getBatchList(model);
		return "batch/batchList";
	}

	/**
	 * 
	 * @param featureIds
	 * @return
	 */
	@PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/featureBatches", method = RequestMethod.POST)
	@ResponseBody
	public Object featureBatches(@RequestBody Long[] featureIds) {

		return batchService.featureBatches(featureIds);
	}
}
