package com.example.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiConfig {

    @Value("${api.url.sports}")
    private String sportsUrl;

    @Value("${api.url.events}")
    private String eventsUrl;

    @Value("${api.url.event.details}")
    private String eventDetailsUrl;
}