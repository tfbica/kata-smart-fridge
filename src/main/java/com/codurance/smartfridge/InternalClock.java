package com.codurance.smartfridge;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InternalClock {

    private final DateTimeFormatter currentDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter itemDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    private LocalDateTime localDateTime;

    public LocalDateTime getTime() {
        return this.localDateTime;
    }

    public void setTime(String date) {
        this.localDateTime = LocalDate.parse(date, currentDateFormatter).atStartOfDay();
    }

    public LocalDateTime parseDateShortYear(String date) {
        return LocalDate.parse(date, itemDateFormatter).atStartOfDay();
    }

    public void incrementDay() {
        this.localDateTime = this.localDateTime.plusDays(1);
    }
}
