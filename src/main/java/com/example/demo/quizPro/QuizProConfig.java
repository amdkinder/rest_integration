package com.example.demo.quizPro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class QuizProConfig {

    @Bean("quizRTemplate")
    public RestTemplate getQuizRTemplate(){
        return new RestTemplate();
    }
}
