package com.jakubeeee.masterthesis.meteoplugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum CommandUrlParameter {

    NONE(""),
    GET("get"),
    SUM("sum");

    @Getter
    private final String value;

}
