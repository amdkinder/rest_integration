package com.example.demo.jsonplaceholder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonPlaceHolderService {

    private final RestTemplate jsonplaceholderRest;
    private static final String URL = "https://jsonplaceholder.typicode.com";
    private final Logger logger =  LoggerFactory.getLogger(JsonPlaceHolderService.class);

    private ObjectMapper objectMapper;


    public JsonPlaceHolderService(@Qualifier("jsonplaceholderRest") RestTemplate jsonplaceholderRest, ObjectMapper objectMapper) {
        this.jsonplaceholderRest = jsonplaceholderRest;
        this.objectMapper = new ObjectMapper();
    }

    public List<JsonPlaceHolderTODO> getTODOS(){
        logger.info("Request to get todos");
        var response = jsonplaceholderRest
                .getForObject(URL + "/todos", String.class);
        var result = parseStringToTodoObj(response);
        logger.info("Result: {}", response);
        return result
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<JsonPlaceHolderTODO> parseStringToTodoObj(String response) {
        logger.info("Request to parse response.");
        var todoList = new ArrayList<JsonPlaceHolderTODO>();
        try {
            var list = objectMapper.readValue(response, new TypeReference<List<JsonPlaceHolderTODO>>() {});
            todoList.addAll(list);
        } catch (JsonProcessingException e) {
            logger.warn("Can not parse response: {}", e.getMessage());
        }
        logger.info("Response parsed to list: {}", todoList);
        return todoList;
    }
}
