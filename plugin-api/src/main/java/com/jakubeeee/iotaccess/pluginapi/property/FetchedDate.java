package com.jakubeeee.iotaccess.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public sealed class FetchedDate extends FetchedProperty<Instant> permits FetchedNullDate {

    public FetchedDate(@NonNull String key, Instant value) {
        super(key, value);
    }

}
