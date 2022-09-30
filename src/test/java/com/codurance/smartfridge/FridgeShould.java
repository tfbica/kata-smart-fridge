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

@ExtendWith(MockitoExtension.class)
public class FridgeShould {

    @Mock private ItemPrinter itemPrinter;
    @Mock private Shelf shelf;
    @Mock private InternalClock internalClock;
    private Fridge fridge;
    private Item expiredMilk;
    private LocalDateTime initialTime;

    @BeforeEach
    void setUp() {
        fridge = new Fridge(itemPrinter, shelf, internalClock);

        expiredMilk = new Item("Milk",
                LocalDateTime.of(2021, 10, 17, 0, 0, 0),
                "sealed",
                LocalDateTime.of(2021, 10, 18, 0, 0, 0));
        initialTime = LocalDateTime.of(2021, 10, 18, 0, 0, 0);
    }

    @Test
    void set_current_date_to_internal_clock() {

        fridge.setCurrentDate("18/10/2021");
        
        then(internalClock).should().setTime("18/10/2021");
    }
    
    @Test
    void scan_and_add_items_to_shelf() {

        given(internalClock.parseDateShortYear("17/10/21")).willReturn(
                LocalDateTime.of(2021, 10, 17, 0, 0, 0));
        given(internalClock.getTime()).willReturn(initialTime);

        fridge.scanAddedItem("Milk", "17/10/21", "sealed");

        then(shelf).should().add(expiredMilk);
    }

    @Test
    void scan_and_remove_items_from_shelf() {

        fridge.scanRemovedItem("Milk");

        then(shelf).should().remove("Milk");
    }

    @Test
    void degrade_items_in_shelf_when_open_door() {

        fridge.signalFridgeDoorOpened();

        then(shelf).should().degradeItems();
    }

    @Test
    void do_nothing_when_close_door() {

        fridge.signalFridgeDoorClosed();

        then(shelf).shouldHaveNoInteractions();
    }
    
    @Test
    void request_printer_to_print_items_state() {

        given(shelf.getItems()).willReturn(Set.of(expiredMilk));

        fridge.showDisplay();

        then(itemPrinter).should().print(Set.of(expiredMilk));
    }
    
    @Test
    void increment_clock_day_on_day_over() {
        
        fridge.simulateDayOver();

        then(internalClock).should().incrementDay();
    }
}
