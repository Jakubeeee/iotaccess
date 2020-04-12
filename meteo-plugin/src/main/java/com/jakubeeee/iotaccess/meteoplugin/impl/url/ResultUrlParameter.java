package com.jakubeeee.iotaccess.meteoplugin.impl.url;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResultUrlParameter {

    NONE(""),
    JSON("json"),
    XML("xml"),
    TXT("txt");

    @Getter
    private final String value;

}
