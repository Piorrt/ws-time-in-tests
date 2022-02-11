package com.example.clock.domain.time;

import java.time.Instant;

public interface TimeProvider {

    Instant getInstantNow();

}