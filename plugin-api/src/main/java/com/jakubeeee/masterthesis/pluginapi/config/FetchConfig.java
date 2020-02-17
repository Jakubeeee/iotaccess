package com.jakubeeee.masterthesis.pluginapi.config;

import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration value object used for specifying the way that external data is fetched.
 */
@RequiredArgsConstructor(staticName = "of")
@Value
public class FetchConfig {

    private final String url;

    private final DataFormat dataFormat;

    private final DataType dataType;

}
