package com.tinder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tinder.client.RestClient;
import com.tinder.constants.TinderConstants;
import com.tinder.data.request.SwipeDataRequest;
import com.tinder.data.response.RefreshDataResponse;
import com.tinder.data.response.SwipeDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;

@Service
public class TinderService {
    @Autowired
    private RestClient restClient;
    @Autowired
    private Gson gson;

    public Logger logger = LoggerFactory.getLogger(TinderService.class.getName());

    public String getRecommendations(String xAuthToken)
    {
        return restClient.get(TinderConstants.RECS,xAuthToken);
    }
    public String getTeasers(String xAuthToken)
    {
        return restClient.get(TinderConstants.TEASER,xAuthToken);
    }
    public String getProfile(String xAuthToken)
    {
        return restClient.get(TinderConstants.PROFILE,xAuthToken);
    }

    public SwipeDataResponse swipes(List<SwipeDataRequest> swipeDataList,String xAuthToken) {
        SwipeDataResponse mySwipeDataResponse = new SwipeDataResponse();
        for(SwipeDataRequest swipeData : swipeDataList)
        {
            String myUrl = null;
            if(swipeData.isLiked())
            {
                myUrl = TinderConstants.LIKE;
            }
            else
            {
                myUrl = TinderConstants.PASS;
            }
            myUrl = myUrl.replace("{USER_ID}",swipeData.getUserID());
            myUrl = myUrl.replace("{S_NUMBER}",swipeData.getSNumber());

            try
            {
                String myResponse = restClient.get(myUrl,xAuthToken);
                mySwipeDataResponse.getResponseList().add(myResponse);
            }
            catch(HttpClientErrorException clientErrorException)
            {
                if(clientErrorException.getRawStatusCode() == 429)
                {
                    try
                    {
                        logger.info("You're going too fast. Resubmitting data.");
                        Thread.sleep(1000);
                        String myResponse = restClient.get(myUrl,xAuthToken);
                        mySwipeDataResponse.getResponseList().add(myResponse);
                    }
                    catch (InterruptedException e)
                    {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        return mySwipeDataResponse;
    }

    public String getRefreshedData(String xAuthToken)
    {
        RefreshDataResponse refreshDataResponse = new RefreshDataResponse();

        JsonObject myProfileResponse = gson.fromJson(getProfile(xAuthToken), JsonObject.class);
        JsonObject myRecommendationResponse = gson.fromJson(getRecommendations(xAuthToken), JsonObject.class);
        JsonObject myTeaserResponse = gson.fromJson(getTeasers(xAuthToken), JsonObject.class);

        refreshDataResponse.setProfile(myProfileResponse);

        if(myRecommendationResponse.getAsJsonObject().get("meta").getAsJsonObject().get("status").getAsInt() == 200)
        {
            JsonArray recommendationArray = myRecommendationResponse.getAsJsonObject().get("data")
                    .getAsJsonObject().get("results").getAsJsonArray();
            refreshDataResponse.setRecommendationList(recommendationArray);
        }
        if(myTeaserResponse.getAsJsonObject().get("meta").getAsJsonObject().get("status").getAsInt() == 200)
        {
            JsonArray teaserArray = myTeaserResponse.getAsJsonObject().get("data")
                    .getAsJsonObject().get("results").getAsJsonArray();
            refreshDataResponse.setTeaserList(teaserArray);

        }
        return gson.toJson(refreshDataResponse);
    }

    public String login(HashMap<String,String> myJsonObject) throws JsonProcessingException {
        return restClient.post(TinderConstants.INITIAL_LOGIN,myJsonObject);
    }

    public String validate(HashMap<String,String> myJsonObject) throws JsonProcessingException {
        return restClient.post(TinderConstants.LOGIN_VALIDATION,myJsonObject);
    }

    public String getSmsToken(HashMap<String,String> myJsonObject) throws JsonProcessingException {
        return restClient.post(TinderConstants.RETRIEVE_SMS_TOKEN,myJsonObject);
    }


}
