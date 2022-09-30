package com.codurance.smartfridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InternalClockShould {

    private InternalClock internalClock;
    private LocalDateTime expectedDateTime;

    @BeforeEach
    void setUp() {
        internalClock = new InternalClock();
        expectedDateTime = LocalDateTime.of(2021, 10, 18, 0, 0, 0);
    }

    @Test
    void keep_date() {

        internalClock.setTime("18/10/2021");

        assertThat(internalClock.getTime()).isEqualTo(expectedDateTime);
    }

    @Test
    void parse_item_date() {

        assertThat(internalClock.parseDateShortYear("18/10/21")).isEqualTo(expectedDateTime);
    }

    @Test
    void increment_day() {

        internalClock.setTime("18/10/2021");
        internalClock.incrementDay();
        assertThat(internalClock.getTime()).isEqualTo(
                LocalDateTime.of(2021, 10, 19, 0, 0, 0));
    }
}