package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record DynamicTaskParameter(@NonNull String groupName, @NonNull String name, @NonNull String value)
        implements TaskParameter<String> {

    public DynamicTaskParameter(@NonNull String fullName, @NonNull String value) {
        this(fullName.split("\\.")[0], fullName.split("\\.")[1], value);
    }

}
