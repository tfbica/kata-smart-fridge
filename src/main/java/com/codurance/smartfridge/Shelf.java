package com.codurance.smartfridge;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Shelf {

    private final Collection<Item> items = new HashSet<>();

    public void add(Item item) {
        items.add(item);
    }

    public void remove(String itemName) {
        items.removeIf(item -> item.getName().equals(itemName));
    }

    public void degradeItems() {
        items.forEach(Item::degradeItem);
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items);
    }
}
