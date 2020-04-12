package com.jakubeeee.iotaccess.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FetchedDate extends FetchedProperty<Instant> {

    public FetchedDate(@NonNull String key, Instant value) {
        super(key, value);
    }

}
