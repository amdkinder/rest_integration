package com.example.demo.jsonplaceholder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JsonPlaceHolderConfig {

    @Bean("jsonplaceholderRest")
    public RestTemplate getJsonplaceholderRest() {
        var restTemplate = new RestTemplate();
        /**
         * TODO: set configs for rest template
         */
        return restTemplate;
    }


}
