package com.codurance.smartfridge;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

public class Item {
    private final String name;
    private LocalDateTime expiryDate;
    private final ItemStatus status;
    private final LocalDateTime addedDate;

    public Item(String name, LocalDateTime expiryDate, ItemStatus status, LocalDateTime addedDate) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.status = status;
        this.addedDate = addedDate;
    }

    public String getName() {
        return this.name;
    }

    public void degradeItem() {
        this.expiryDate = this.expiryDate.minusHours(status.getDegradationInHours());
    }

    public int daysRemaining(LocalDateTime currentTime) {
        Duration difference = Duration.between(currentTime, expiryDate);
        if (isLessThanOneDayInThePast(difference)) {
            return -1;
        }
        return Math.toIntExact(difference.toDaysPart());
    }

    private boolean isLessThanOneDayInThePast(Duration difference) {
        return difference.toHoursPart() < 0 && difference.toDaysPart() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new EqualsBuilder().append(name, item.name).append(expiryDate, item.expiryDate).append(status, item.status).append(addedDate, item.addedDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(expiryDate).append(status).append(addedDate).toHashCode();
    }
}
