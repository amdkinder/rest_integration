package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainResource {

    @GetMapping
    public String getMain() {
        return "Hello Integration Spring demo";
    }
}
