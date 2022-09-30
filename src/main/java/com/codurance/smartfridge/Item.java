package com.codurance.smartfridge;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

public class Item {
    private final String name;
    private LocalDateTime expiryDate;
    private final String sealed;
    private final LocalDateTime addedDate;

    public Item(String name, LocalDateTime expiryDate, String sealed, LocalDateTime addedDate) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.sealed = sealed;
        this.addedDate = addedDate;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void degradeItem() {
        if (this.sealed.equals("sealed")) {
            this.expiryDate = this.expiryDate.minusHours(1);
            return;
        }
        this.expiryDate = this.expiryDate.minusHours(5);
    }

    public int daysRemaining(LocalDateTime currentTime) {
        Duration difference = Duration.between(currentTime, expiryDate);
        if (isLessThanOneDayInThePast(difference)) {
            return -1;
        }
        return Math.toIntExact(difference.toDaysPart());
    }

    private static boolean isLessThanOneDayInThePast(Duration difference) {
        return difference.toMinutes() < 0 && difference.toDays() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new EqualsBuilder().append(name, item.name).append(expiryDate, item.expiryDate).append(sealed, item.sealed).append(addedDate, item.addedDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(expiryDate).append(sealed).append(addedDate).toHashCode();
    }
}
