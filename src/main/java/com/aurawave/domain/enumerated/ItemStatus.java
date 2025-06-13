package com.aurawave.domain.enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ItemStatus {
    AVALIABLE,
    USED,
    DAMAGED,
    EXPIRED,
    DISCARDED;

    @JsonCreator
    public static ItemStatus forValue(String value) {
        return ItemStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}