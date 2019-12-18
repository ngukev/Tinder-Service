package com.tinder.service;

import com.tinder.client.RestClient;
import com.tinder.constants.TinderConstants;
import com.tinder.data.SwipeData;
import com.tinder.data.SwipeDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TinderService {
    @Autowired
    private RestClient restClient;

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


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

    public SwipeDataResponse swipes(List<SwipeData> swipeDataList)
    {
        SwipeDataResponse mySwipeDataResponse = new SwipeDataResponse();
        for(SwipeData swipeData : swipeDataList)
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
                int mySleepMilliSeconds = 100 * getRandomNumberInRange(4,6);
                Thread.sleep(mySleepMilliSeconds);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        return mySwipeDataResponse;
    }
}
