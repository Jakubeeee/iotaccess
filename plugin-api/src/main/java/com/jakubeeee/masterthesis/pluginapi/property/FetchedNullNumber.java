package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public final class FetchedNullNumber extends FetchedNumber {

    public FetchedNullNumber(@NonNull String key) {
        super(key, null);
    }

}
