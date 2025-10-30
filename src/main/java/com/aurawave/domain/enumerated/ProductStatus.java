package com.aurawave.domain.enumerated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    ACTIVE,
    INACTIVE,
    EXPIRED;

    @JsonCreator
    public static ProductStatus forValue(String value) {
        return ProductStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}