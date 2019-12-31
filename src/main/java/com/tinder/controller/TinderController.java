package com.tinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tinder.data.request.SwipeDataRequest;
import com.tinder.data.response.SwipeDataResponse;
import com.tinder.service.TinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@CrossOrigin
@RestController
public class TinderController {

	private Logger logger = LoggerFactory.getLogger(TinderController.class.getName());

	@Autowired
	private TinderService tinderService;
	@RequestMapping(value = "/recommendations",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRecommendations(@RequestHeader("x-auth-token") String xAuthToken) {
		logger.info("Received Request for getRecommendations");
		String myResult = tinderService.getRecommendations(xAuthToken);
		logger.info("Here's the getRecommendations response: " + myResult);

		return myResult;
	}
	@RequestMapping(value = "/teasers",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTeaser(@RequestHeader("x-auth-token") String xAuthToken) {
		logger.info("Received Request for getTeasers");
		String myResult = tinderService.getTeasers(xAuthToken);
		logger.info("Here's the getTeasers response: " + myResult);

		return myResult;
	}

	@RequestMapping(value = "/profile",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProfile(@RequestHeader("x-auth-token") String xAuthToken) {
		logger.info("Received Request for getProfile");
		String myResult = tinderService.getProfile(xAuthToken);
		logger.info("Here's the getProfile response: " + myResult);

		return myResult;
	}

	@PostMapping(path = "/swipes", produces = MediaType.APPLICATION_JSON_VALUE)
	public SwipeDataResponse swipes(@RequestHeader("x-auth-token") String xAuthToken ,
									@RequestBody List<SwipeDataRequest> swipeDataList) throws InterruptedException {
		int likedSwipesCount = 0;
		int passSwipesCount = 0;
		for(SwipeDataRequest swipeData : swipeDataList)
		{
			if(swipeData.isLiked())
			{
				likedSwipesCount++;
			}
			else
			{
				passSwipesCount++;
			}
		}
		logger.info("Received Request for swipes for Likes: " + likedSwipesCount + " passes: " + passSwipesCount);
		SwipeDataResponse myResultList = tinderService.swipes(swipeDataList,xAuthToken);
		logger.info("Here's the swipes response: " + myResultList);
		return myResultList;
	}

	@RequestMapping(value = "/refresh",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshData(@RequestHeader("x-auth-token") String xAuthToken) {
		logger.info("Received Request for refresh");
		String refreshDataResponse = tinderService.getRefreshedData(xAuthToken);
		logger.info("Here's the refresh response" + refreshDataResponse);
		return refreshDataResponse;
	}

	@PostMapping(value = "/login",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestBody HashMap<String, String> myJsonObject) throws JsonProcessingException {
		logger.info("Received Request for login");
		String myResponse = tinderService.login(myJsonObject);
		logger.info("Here's the login response");
		return myResponse;
	}

	@PostMapping(value = "/validate",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String validateLogin(@RequestBody HashMap<String, String> myJsonObject) throws JsonProcessingException {
		logger.info("Received Request for validate");
		String myResponse = tinderService.validate(myJsonObject);
		logger.info("Here's the validate response");
		return myResponse;
	}

	@PostMapping(value = "/get-sms-token",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getSmsToken(@RequestBody HashMap<String, String> myJsonObject) throws JsonProcessingException {
		logger.info("Received get-sms-token for validate");
		String myResponse = tinderService.getSmsToken(myJsonObject);
		logger.info("Here's the get-sms-token response");
		return myResponse;
	}

}
