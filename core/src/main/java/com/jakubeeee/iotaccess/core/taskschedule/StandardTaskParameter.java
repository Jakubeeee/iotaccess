package com.jakubeeee.iotaccess.core.taskschedule;

import lombok.NonNull;

public record StandardTaskParameter<T>(@NonNull String groupName, @NonNull String name, @NonNull T value)
        implements TaskParameter<T> {

    public StandardTaskParameter(@NonNull String fullName, @NonNull T value) {
        this(fullName.split("\\.")[0], fullName.split("\\.")[1], value);
    }

}
