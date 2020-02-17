package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public final class FetchedDate extends FetchedProperty<Instant> {

    public FetchedDate(@NonNull String key, Instant value) {
        super(key, value);
    }

}
