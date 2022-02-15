package com.example.clock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = ClockApplication.class
)
@ExtendWith(SpringExtension.class)
public class BaseIT {

    protected String localUrl(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

}