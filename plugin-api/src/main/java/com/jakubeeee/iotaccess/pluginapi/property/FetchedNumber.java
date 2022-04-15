package com.jakubeeee.iotaccess.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public sealed class FetchedNumber extends FetchedProperty<BigDecimal> permits FetchedNullNumber {

    public FetchedNumber(@NonNull String key, BigDecimal value) {
        super(key, value);
    }

}
