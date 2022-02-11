package com.example.clock;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Component
@Primary
public class MutableClock extends Clock {

    private Clock fixed;

    public MutableClock() {
        reset();
    }

    public void set(String text){
        fixed = Clock.fixed(Instant.parse(text), ZoneId.of("Europe/Warsaw"));
    }

    public void reset() {
        this.fixed = Clock.fixed(Instant.parse("2010-01-01T08:00:00Z"), ZoneId.of("Europe/Warsaw"));
    }

    @Override
    public ZoneId getZone() {
        return fixed.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return fixed.withZone(zone);
    }

    @Override
    public Instant instant() {
        return fixed.instant();
    }
}
