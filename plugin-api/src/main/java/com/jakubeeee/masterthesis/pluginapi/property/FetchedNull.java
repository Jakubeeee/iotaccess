package com.jakubeeee.masterthesis.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public class FetchedNull extends FetchedProperty<Void> {

    public FetchedNull(@NonNull String key) {
        super(key, null);
    }

}
