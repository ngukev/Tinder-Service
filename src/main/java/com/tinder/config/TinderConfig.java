package com.tinder.config;

import com.google.gson.Gson;
import com.tinder.client.RestClient;
import com.tinder.service.TinderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class TinderConfig {
    @Bean
    public RestClient getRestClient(@Value("${XAuthToken}") String xAuthToken){return new RestClient(xAuthToken);}
    @Bean
    public TinderService getTinderService(){return new TinderService();}
    @Bean
    public Gson getGson(){return new Gson();}

}
