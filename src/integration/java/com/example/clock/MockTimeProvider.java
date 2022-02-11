package com.example.clock;

import com.example.clock.domain.time.TimeProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Primary
public class MockTimeProvider implements TimeProvider {

    private Instant instant;

    public MockTimeProvider() {
        reset();
    }

    public void plusDays(long daysToAdd) {
        instant = instant.plus(daysToAdd, ChronoUnit.DAYS);
    }

    public void reset() {
        instant = Instant.parse("2010-01-01T00:00:00Z");
    }

    public void set(String time) {
        instant = Instant.parse(time);
    }

    @Override
    public Instant getInstantNow() {
        return instant;
    }
}