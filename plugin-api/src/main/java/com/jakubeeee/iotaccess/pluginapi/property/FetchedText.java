package com.jakubeeee.iotaccess.pluginapi.property;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public sealed class FetchedText extends FetchedProperty<String> permits FetchedNullText {

    public FetchedText(@NonNull String key, String value) {
        super(key, value);
    }

}
