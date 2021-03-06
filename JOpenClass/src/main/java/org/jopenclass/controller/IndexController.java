package org.jopenclass.controller;

import org.jopenclass.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@Autowired
	private BatchService batchService;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String loadIndex(ModelMap model) {
		batchService.getFeaturedBatchList(model);
		return "index";
	}
}
