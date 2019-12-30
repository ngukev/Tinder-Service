package com.tinder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tinder.client.RestClient;
import com.tinder.service.TinderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TinderConfig {
    @Bean
    public RestClient getRestClient(){return new RestClient();}
    @Bean
    public TinderService getTinderService(){return new TinderService();}
    @Bean
    public Gson getGson(){return new Gson();}
    @Bean
    public ObjectMapper getObjectMapper(){return new ObjectMapper();}

}
