package com.tinder.controller;

import com.google.gson.JsonObject;
import com.tinder.data.request.SwipeDataRequest;
import com.tinder.data.response.RefreshDataResponse;
import com.tinder.data.response.SwipeDataResponse;
import com.tinder.service.TinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
public class TinderController {

	private Logger logger = LoggerFactory.getLogger(TinderController.class.getName());

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

	@RequestMapping(value = "/profile",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProfile() {
		logger.info("Received Request for getProfile");
		String myResult = tinderService.getProfile();
		logger.info("Here's the getProfile response: " + myResult);

		return myResult;
	}

	@PostMapping(path = "/swipes", produces = MediaType.APPLICATION_JSON_VALUE)
	public SwipeDataResponse swipes(@RequestBody List<SwipeDataRequest> swipeDataList) throws InterruptedException {
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
		SwipeDataResponse myResultList = tinderService.swipes(swipeDataList);
		logger.info("Here's the swipes response: " + myResultList);
		return myResultList;
	}

	@RequestMapping(value = "/refresh",  produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshData() {
		logger.info("Received Request for refresh");
		String refreshDataResponse = tinderService.getRefreshedData();
		logger.info("Here's the refresh response" + refreshDataResponse);
		return refreshDataResponse;
	}

}
