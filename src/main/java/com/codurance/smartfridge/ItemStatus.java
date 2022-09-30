package com.codurance.smartfridge;

public enum ItemStatus {
    SEALED(1),
    OPEN(5);

    private final int degradationInHours;

    ItemStatus(int degradationInHours) {
        this.degradationInHours = degradationInHours;
    }

    public int getDegradationInHours() {
        return degradationInHours;
    }
}
