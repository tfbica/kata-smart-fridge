package com.codurance.smartfridge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ItemShould {

    @Test
    void degrade_sealed_item() {
        Item item = new Item("Milk",
                LocalDateTime.of(2021, 10, 17, 0, 0),
                ItemStatus.SEALED,
                LocalDateTime.of(2021, 10, 12, 0, 0));

        item.degradeItem();

        Item expected = new Item("Milk",
                LocalDateTime.of(2021, 10, 16, 23, 0),
                ItemStatus.SEALED,
                LocalDateTime.of(2021, 10, 12, 0, 0));
        assertThat(item).isEqualTo(expected);

    }

    @Test
    void degrade_opened_item() {
        Item item = new Item("Milk",
                LocalDateTime.of(2021, 10, 17, 0, 0),
                ItemStatus.OPEN,
                LocalDateTime.of(2021, 10, 12, 0, 0));

        item.degradeItem();

        Item expected = new Item("Milk",
                LocalDateTime.of(2021, 10, 16, 19, 0),
                ItemStatus.OPEN,
                LocalDateTime.of(2021, 10, 12, 0, 0));
        assertThat(item).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "2021-10-17T10:00:00, 2021-10-15, 2",
            "2021-10-17T10:00:00, 2021-10-17, 0",
            "2021-10-17T10:00:00, 2021-10-18, -1", // special case
            "2021-10-17T23:00:00, 2021-10-18, -1", // special case
            "2021-10-17T10:00:00, 2021-10-19, -1",
            "2021-10-22T09:00:00, 2021-10-21, 1",  // Peppers
            "2021-10-21T19:00:00, 2021-10-21, 0",  // Lettuce
            "2021-11-17T17:00:00, 2021-10-21, 27", // Cheese

    })
    void return_remaining_days(LocalDateTime expiryDate, LocalDate localDate, int expectedDaysRemaining) {

        Item item = new Item("Milk", expiryDate, ItemStatus.SEALED, expiryDate);
        assertThat(item.daysRemaining(localDate.atStartOfDay())).isEqualTo(expectedDaysRemaining);

    }
}