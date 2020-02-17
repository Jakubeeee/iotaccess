package com.jakubeeee.masterthesis.meteoplugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum ResultUrlParameter {

    NONE(""),
    JSON("json"),
    XML("xml"),
    TXT("txt");

    @Getter
    private final String value;

}
