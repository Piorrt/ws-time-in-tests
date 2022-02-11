package com.example.clock.external.time;

import com.example.clock.domain.time.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RealTimeProvider implements TimeProvider {

    @Override
    public Instant getInstantNow() {
        return Instant.now();
    }

}