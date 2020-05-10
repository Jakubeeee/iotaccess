package com.jakubeeee.iotaccess.pluginapi.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for associating a process with converter.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public final class ConverterConfig {

    @NonNull
    private final String converterIdentifier;

}
