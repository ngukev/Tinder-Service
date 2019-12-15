package com.tinder.controller;

import com.tinder.service.TinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class TinderController {

	public Logger logger = LoggerFactory.getLogger(TinderController.class.getName());

	@Autowired
	private TinderService tinderService;
	@RequestMapping(value = "/recommendations",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRecommendations() {
		logger.info("Received Request for getRecommendations");
		String myResult = tinderService.getRecommendations();
		logger.info("Here's the getRecommendations response: " + myResult);

		return myResult;
	}
	@RequestMapping(value = "/teasers",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTeaser() {
		logger.info("Received Request for getTeasers");
		String myResult = tinderService.getTeasers();
		logger.info("Here's the getTeasers response: " + myResult);

		return myResult;
	}
}
