package com.tinder.client;
import com.tinder.constants.TinderConstants;
import com.tinder.controller.TinderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    private String server = TinderConstants.BASE_URL;
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;


    public Logger logger = LoggerFactory.getLogger(RestClient.class.getName());

    public RestClient(String xAuthToken) {

        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("User-Agent", "Tinder/11.4.0 (iPhone; iOS 12.4.1; Scale/2.00)");
        headers.add("X-Auth-Token", xAuthToken);
    }

    public String get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}