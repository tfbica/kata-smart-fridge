package com.codurance.smartfridge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ItemPrinterShould {

    @Mock private Console console;
    @Mock private InternalClock internalClock;
    @Mock private Item item;
    private LocalDateTime currentDate;
    private ItemPrinter printer;

    @BeforeEach
    public void setup() {
        currentDate = LocalDateTime.of(2021, 10, 18, 0, 0);
        printer = new ItemPrinter(internalClock, console);
    }

    @Test
    void print_expired_item() {
        givenWithNameAndDaysRemaining(item, "Milk", -1);

        printer.print(Set.of(item));

        then(console).should().println("EXPIRED: Milk");
    }

    @Test
    void print_item_remaining_1_day() {
        givenWithNameAndDaysRemaining(item, "Milk", 1);

        printer.print(Set.of(item));

        then(console).should().println("Milk: 1 day remaining");
    }

    @Test
    void print_item_remaining_0_days() {
        givenWithNameAndDaysRemaining(item, "Milk", 0);

        printer.print(Set.of(item));

        then(console).should().println("Milk: 0 days remaining");
    }

    @Test
    void print_item_remaining_10_days() {
        givenWithNameAndDaysRemaining(item, "Milk", 10);

        printer.print(Set.of(item));

        then(console).should().println("Milk: 10 days remaining");
    }

    @Test
    void print_items_by_expiry() {
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);

        givenWithNameAndDaysRemaining(item1, "Milk", -1);
        givenWithNameAndDaysRemaining(item2, "Peppers", 1);
        givenWithNameAndDaysRemaining(item3, "Cheese", 27);

        printer.print(Set.of(item2, item3, item1));

        then(console).should().println("EXPIRED: Milk");
        then(console).should().println("Peppers: 1 day remaining");
        then(console).should().println("Cheese: 27 days remaining");
    }

    private void givenWithNameAndDaysRemaining(Item item, String name, int daysRemaining) {
        given(internalClock.getTime()).willReturn(currentDate);
        given(item.getName()).willReturn(name);
        given(item.daysRemaining(currentDate)).willReturn(daysRemaining);
    }
}