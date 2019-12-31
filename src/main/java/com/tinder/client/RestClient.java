package com.tinder.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinder.constants.TinderConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

public class RestClient {

    private String server = TinderConstants.BASE_URL;
    private RestTemplate rest = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;


    private HttpHeaders generateHttpHeaders(String xAuthToken)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("User-Agent", "Tinder/11.4.0 (iPhone; iOS 12.4.1; Scale/2.00)");
        if(xAuthToken != null)
        {
            headers.add("x-auth-token",xAuthToken);
        }
        return headers;
    }

    public String get(String uri, String xAuthToken) {

        String finalUrl = server + uri;
        if(finalUrl.contains("pass") || finalUrl.contains("like"))
        {
            finalUrl = finalUrl.replace("v2/","");
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(finalUrl);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, generateHttpHeaders(xAuthToken));
        ResponseEntity<String> responseEntity = rest.exchange(builder.build(true).toUri(), HttpMethod.GET, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public String post(String uri, HashMap<String,String> myRequestBody) throws JsonProcessingException {
        String finalUrl = server + uri;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(finalUrl);
        HttpEntity<String> requestEntity = new HttpEntity<String>(objectMapper.writeValueAsString(myRequestBody), generateHttpHeaders(null));
        ResponseEntity<String> responseEntity = rest.exchange(builder.build(true).toUri(), HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

}