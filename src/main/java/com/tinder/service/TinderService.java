package com.tinder.service;

import com.tinder.client.RestClient;
import com.tinder.constants.TinderConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class TinderService {
    @Autowired
    private RestClient restClient;
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
}
