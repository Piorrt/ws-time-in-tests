package com.example.clock;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = {ClockApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class BaseIT {

    //TODO 4 Dodaj implementację TimeProvider -> MockTimeProvider

    //TODO 5 Dodaj MockTimeProvider do BaseIT

    protected String localUrl(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

}