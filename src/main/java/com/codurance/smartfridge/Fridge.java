package com.codurance.smartfridge;

public class Fridge {

    private ItemPrinter printer;
    private Shelf shelf;
    private InternalClock internalClock;

    public Fridge(Console console) {
        InternalClock internalClock = new InternalClock();
        initialize(new ItemPrinter(internalClock, console), new Shelf(), internalClock);
    }

    public Fridge(ItemPrinter printer, Shelf shelf, InternalClock internalClock) {
        initialize(printer, shelf, internalClock);
    }

    private void initialize(ItemPrinter printer, Shelf shelf, InternalClock internalClock) {
        this.printer = printer;
        this.shelf = shelf;
        this.internalClock = internalClock;
    }

    public void setCurrentDate(String date) {
        internalClock.setTime(date);
    }

    public void signalFridgeDoorOpened() {
        shelf.degradeItems();
    }

    public void scanAddedItem(String name, String expiryDate, String sealed) {
        shelf.add(new Item(name,
                internalClock.parseDateShortYear(expiryDate),
                sealed,
                internalClock.getTime()));
    }

    public void signalFridgeDoorClosed() {
        // does nothing
    }

    public void simulateDayOver() {
        this.internalClock.incrementDay();
    }

    public void scanRemovedItem(String name) {
        shelf.remove(name);
    }

    public void showDisplay() {
        printer.print(shelf.getItems());
    }
}
