package com.jakubeeee.masterthesis.meteoplugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum IdentifierUrlParameter {

    NONE(""),
    HOUR0("0000"),
    HOUR1("0001"),
    HOUR2("0002"),
    HOUR3("0003"),
    HOUR4("0004"),
    HOUR5("0005"),
    HOUR6("0006"),
    HOUR7("0007"),
    HOUR8("0008"),
    HOUR9("0009"),
    HOUR10("00010"),
    HOUR11("00011");

    @Getter
    private final String value;

}
