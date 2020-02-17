package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public final class FetchedNumber extends FetchedProperty<BigDecimal> {

    public FetchedNumber(@NonNull String key, BigDecimal value) {
        super(key, value);
    }

}
