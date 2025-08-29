package com.backend.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestApi {

    @GetMapping("/api")
    public String hello() {
        return "We have launched!";
    }

}
