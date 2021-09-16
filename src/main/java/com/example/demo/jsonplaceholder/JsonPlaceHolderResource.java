package com.example.demo.jsonplaceholder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json-place-holder")
public class JsonPlaceHolderResource {

    private final JsonPlaceHolderService service;

    public JsonPlaceHolderResource(JsonPlaceHolderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getTodos() {
        var result = service.getTODOS();
        return ResponseEntity.ok(result);
    }
}
