package com.tinder.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tinder.client.RestClient;
import com.tinder.constants.TinderConstants;
import com.tinder.data.request.SwipeDataRequest;
import com.tinder.data.response.RefreshDataResponse;
import com.tinder.data.response.SwipeDataResponse;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Random;

@Service
public class TinderService {
    @Autowired
    private RestClient restClient;
    @Autowired
    private Gson gson;

    public Logger logger = LoggerFactory.getLogger(TinderService.class.getName());

    public String getRecommendations()
    {
        return restClient.get(TinderConstants.RECS);
    }
    public String getTeasers()
    {
        return restClient.get(TinderConstants.TEASER);
    }
    public String getProfile()
    {
        return restClient.get(TinderConstants.PROFILE);
    }

    public SwipeDataResponse swipes(List<SwipeDataRequest> swipeDataList) {
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
                String myResponse = restClient.get(myUrl);
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
                        String myResponse = restClient.get(myUrl);
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

    public String getRefreshedData()
    {
        RefreshDataResponse refreshDataResponse = new RefreshDataResponse();

        JsonObject myProfileResponse = gson.fromJson(getProfile(), JsonObject.class);
        JsonObject myRecommendationResponse = gson.fromJson(getRecommendations(), JsonObject.class);
        JsonObject myTeaserResponse = gson.fromJson(getTeasers(), JsonObject.class);

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


}
