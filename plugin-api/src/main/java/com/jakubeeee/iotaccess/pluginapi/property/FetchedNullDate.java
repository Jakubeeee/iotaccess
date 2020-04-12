package com.jakubeeee.iotaccess.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public final class FetchedNullDate extends FetchedDate {

    public FetchedNullDate(@NonNull String key) {
        super(key, null);
    }

}
