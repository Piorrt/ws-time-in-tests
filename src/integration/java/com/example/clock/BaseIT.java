package com.example.clock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = {ClockApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class BaseIT {

    @Autowired
    protected MockTimeProvider timeProvider;

    protected String localUrl(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

}