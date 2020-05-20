package com.jakubeeee.iotaccess.core.dynamicconfig;

import lombok.NonNull;

public record DynamicConfigEntry(@NonNull String key, @NonNull String value) implements Comparable<DynamicConfigEntry> {

    @Override
    public int compareTo(@NonNull DynamicConfigEntry other) {
        return key.compareTo(other.key());
    }

}
