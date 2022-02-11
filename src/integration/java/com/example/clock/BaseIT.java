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

    //TODO - 4. zdefinij beana MutableClock który rozszerza Clock
    // zaimplementuj wszystkie metody interface
    // dodaj metody
    //       - set - pozwala na ustawienie aktualnej daty i czasu
    //       - reset - resetuje czas do okreśonej daty i czasu


    //TODO - 5. dodaj MutableClock do BaseIT

    protected String localUrl(String endpoint) {
        return "http://localhost:8080" + endpoint;
    }

}