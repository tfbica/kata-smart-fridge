package com.codurance.smartfridge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class FridgeFeature {

    @Mock private Console console;

    @Test
    void interact() {

        Fridge fridge = new Fridge(console);
        InOrder inOrder = Mockito.inOrder(console);

        fridge.setCurrentDate("18/10/2021");

        fridge.signalFridgeDoorOpened();
        fridge.scanAddedItem("Milk", "21/10/21", ItemStatus.SEALED);
        fridge.scanAddedItem("Cheese", "18/11/21", ItemStatus.SEALED);
        fridge.scanAddedItem("Beef", "20/10/21", ItemStatus.SEALED);
        fridge.scanAddedItem("Lettuce", "22/10/21", ItemStatus.SEALED);
        fridge.signalFridgeDoorClosed();

//        fridge.showDisplay();
//        then(console).should(inOrder).println("Beef: 2 days remaining");
//        then(console).should(inOrder).println("Milk: 3 days remaining");
//        then(console).should(inOrder).println("Lettuce: 4 days remaining");
//        then(console).should(inOrder).println("Cheese: 31 days remaining");

        fridge.simulateDayOver(); // 19

        fridge.signalFridgeDoorOpened();
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.scanRemovedItem("Milk");
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.scanAddedItem("Milk", "26/10/21", ItemStatus.OPEN);
        fridge.scanAddedItem("Peppers", "23/10/21", ItemStatus.OPEN);
        fridge.signalFridgeDoorClosed();

        fridge.simulateDayOver();  // 20

        fridge.signalFridgeDoorOpened();
        fridge.scanRemovedItem("Beef");
        fridge.scanRemovedItem("Lettuce");
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.scanAddedItem("Lettuce", "22/10/21", ItemStatus.OPEN);
        fridge.scanAddedItem("Beef", "20/10/21", ItemStatus.OPEN);  // NEW
        fridge.signalFridgeDoorClosed();

        fridge.signalFridgeDoorOpened();
        fridge.signalFridgeDoorClosed();

        fridge.simulateDayOver(); // 21

        fridge.showDisplay();

        then(console).should(inOrder).println("EXPIRED: Beef");
        then(console).should(inOrder).println("Lettuce: 0 days remaining");
        then(console).should(inOrder).println("Peppers: 1 day remaining");
        then(console).should(inOrder).println("Milk: 4 days remaining");
        then(console).should(inOrder).println("Cheese: 27 days remaining");

//        then(console).should().println("EXPIRED: Milk");
//        then(console).should().println("Lettuce: 0 days remaining");
//        then(console).should().println("Peppers: 1 day remaining");
//        then(console).should().println("Cheese: 31 days remaining");
    }
}
