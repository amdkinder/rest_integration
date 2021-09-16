package com.example.demo.quizPro;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isNotBlank;


@Service
public class QuizService {

    private final RestTemplate quizRTemplate;
    private HttpHeaders httpHeaders;
    private static final String URL = "http://localhost:8080/api";
    private String idToken = null;
    private final ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(QuizService.class);

    public QuizService(@Qualifier("quizRTemplate") RestTemplate quizRTemplate) {
        this.quizRTemplate = quizRTemplate;
        this.objectMapper = new ObjectMapper();
        httpHeaders = getHeaders();
    }


    public String getToken() {

        var body = convert2Obj(new LoginRequest("admin", "admin"));
        var request = new HttpEntity<>(body, httpHeaders);

        String token = "";

        try {
            var response = this.quizRTemplate.postForEntity(URL + "/authenticate", request, String.class);
            log.info("Response: {}", response);
            if (isNotBlank(response.getBody())) {
                token = objectMapper.readValue(response.getBody(), LoginResponse.class).getToken();
            }
        } catch (Exception e) {
            log.warn("Can not connect to Quiz Server: {}", e.getMessage());
        }

        return token;
    }


    public List<Question> getQuestions() {
        var suffixUrl = "/questions";
        var request = new HttpEntity<>(httpHeaders);
        var response = requestWithToken(URL + suffixUrl, HttpMethod.GET, request);

        var questions = new ArrayList<Question>();

        try {
            /*
            TODO => objectMapper.readValue(response.getBody(), responseObjectType.class);
               new TypeReference<List<Question>>(){} ==> Bu degani question objectlik listni tipi shunaqa degani
             */
            questions.addAll(objectMapper.readValue(response.getBody(), new TypeReference<List<Question>>(){}));
        } catch (Exception e) {
            log.warn("Can not parse response body to Question class: {}", response.getBody());
        }

        return questions;
    }


    // UTILS

    private ResponseEntity<String> requestWithToken(String url, HttpMethod method, HttpEntity<?> request) {

        getCurrentToken();
        log.info("Request: {}", request);

        try {
            var response = this.quizRTemplate.exchange(url, method, request, String.class);

            if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                login();
                response = this.quizRTemplate.postForEntity(url, request, String.class);
            }

            log.info("Response: {}", response);

            return response;

        } catch (Exception e) {
            log.warn("Can not connect url: {} {} {}", method, url, request);
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }


    private Map<String, Object> convert2Obj(Object object) {
        return objectMapper.convertValue(object, Map.class);
    }

    private String getCurrentToken() {
        if (idToken == null) {
            login();
        }
        return idToken;
    }

    public void login() {
        idToken = getToken();
        httpHeaders.setBearerAuth(idToken);
    }

    private HttpHeaders getHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.ALL));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
