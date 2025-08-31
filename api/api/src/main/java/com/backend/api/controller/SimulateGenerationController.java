package com.backend.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.services.GeneratorService;

@RestController
@RequestMapping("/api/simulate")
public class SimulateGenerationController {
    private final GeneratorService generatorService;

    public SimulateGenerationController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @PostMapping("/start")
    public void start(
            @RequestParam int ratePerSecond) {
        generatorService.start(ratePerSecond);
    }

    @PostMapping("/stop")
    public void stop() {
        generatorService.stop();
    }
}
