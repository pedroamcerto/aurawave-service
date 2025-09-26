package com.aurawave.core.domain.enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventType {
    INPUT,
    EXIT,
    ADJUSTMENT,
    MOVEMENT;

    @JsonCreator
    public static EventType forValue(String value) {
        return EventType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
