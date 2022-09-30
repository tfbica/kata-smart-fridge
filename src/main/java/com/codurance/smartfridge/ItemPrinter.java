package com.codurance.smartfridge;

import java.util.Collection;

public class ItemPrinter {

    private static final String EXPIRED = "EXPIRED";
    private static final String DAY_REMAINING = " day remaining";
    private static final String DAYS_REMAINING = " days remaining";

    private final InternalClock internalClock;
    private final Console console;

    public ItemPrinter(InternalClock internalClock, Console console) {
        this.internalClock = internalClock;
        this.console = console;
    }
    public void print(Collection<Item> items) {
        items.stream().sorted((o1, o2) -> o1.daysRemaining(internalClock.getTime()) - o2.daysRemaining(internalClock.getTime()))
                .forEach(item -> console.println(buildStringFor(item)));
    }

    private String buildStringFor(Item item) {
        int daysRemaining = item.daysRemaining(internalClock.getTime());
        if (daysRemaining < 0) {
            return appendExpired(item.getName());
        } else {
            return buildRemaining(item.getName(), daysRemaining);
        }
    }

    private String buildRemaining(String name, int daysRemaining) {
        StringBuilder str = new StringBuilder(name).append(": ").append(daysRemaining);
        if (daysRemaining == 1) {
            return str.append(DAY_REMAINING).toString();
        } else {
            return str.append(DAYS_REMAINING).toString();
        }
    }

    private String appendExpired(String name) {
        return EXPIRED + ": " + name;
    }

}
