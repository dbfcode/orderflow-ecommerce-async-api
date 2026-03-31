package com.orderflow.ecommerce.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {

        return Map.of(
                "status", "ok",
                "message", "versão 1.",
                "timestamp", LocalDateTime.now().toString()
        );
    }

}
