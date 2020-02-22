package com.jakubeeee.masterthesis.meteoplugin.impl.url;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandUrlParameter {

    NONE(""),
    GET("get"),
    SUM("sum");

    @Getter
    private final String value;

}
