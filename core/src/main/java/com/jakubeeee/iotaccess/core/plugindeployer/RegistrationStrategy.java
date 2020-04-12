package com.jakubeeee.iotaccess.core.plugindeployer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum RegistrationStrategy {

    SPI("spi"),
    DATABASE("db"),
    FILESYSTEM("fs");

    private final String commandLineOptionValue;

    static Optional<RegistrationStrategy> fromCommandLineOptionValue(String optionValue) {
        return EnumSet.allOf(RegistrationStrategy.class)
                .stream()
                .filter(strategy -> Objects.equals(strategy.getCommandLineOptionValue(), optionValue))
                .findFirst();
    }

}
