package com.jakubeeee.iotaccess.pluginapi.property;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FetchedText extends FetchedProperty<String> {

    public FetchedText(@NonNull String key, String value) {
        super(key, value);
    }

}
