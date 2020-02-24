package com.jakubeeee.masterthesis.pluginapi.config;

import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying the way that external data is fetched.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public class FetchConfig {

    @NonNull
    private final String url;

    @NonNull
    private final DataFormat dataFormat;

    @NonNull
    private final DataType dataType;

}
