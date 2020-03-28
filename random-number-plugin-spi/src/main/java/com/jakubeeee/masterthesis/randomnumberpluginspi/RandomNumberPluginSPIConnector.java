package com.jakubeeee.masterthesis.randomnumberpluginspi;

import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import com.jakubeeee.masterthesis.pluginapi.config.FetchConfig;
import com.jakubeeee.masterthesis.pluginapi.config.PluginConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ProcessConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ScheduleConfig;
import com.jakubeeee.masterthesis.randomnumberpluginspi.impl.RandomNumberConverter;

import java.util.Set;

import static com.jakubeeee.masterthesis.pluginapi.converter.DataFormat.JSON;
import static com.jakubeeee.masterthesis.pluginapi.converter.DataType.STANDARD;
import static com.jakubeeee.masterthesis.randomnumberpluginspi.impl.RandomNumberClientConstants.*;

public class RandomNumberPluginSPIConnector implements PluginConnector {

    private static final String IDENTIFIER = "Random number plugin (SPI)";

    @Override
    public RandomNumberConverter getConverter() {
        return RandomNumberConverter.getInstance();
    }

    @Override
    public PluginConfig getConfig() {
        var singleRandomNumberProcessConfig = ProcessConfig.of(
                "Single random number fetch process (SPI)",
                "",
                FetchConfig.of(GET_RANDOM_NUMBER_PATH, JSON, STANDARD),
                ScheduleConfig.of(30_000, 5_000));

        var threeRandomNumbersProcessConfig = ProcessConfig.of(
                "Three random numbers fetch process (SPI)",
                "",
                FetchConfig.of(GET_3_RANDOM_NUMBERS_PATH, JSON, STANDARD),
                ScheduleConfig.of(120_000, 10_000));

        var tenRandomNumbersProcessConfig = ProcessConfig.of(
                "Ten random numbers fetch process (SPI)",
                "",
                FetchConfig.of(GET_10_RANDOM_NUMBERS_PATH, JSON, STANDARD),
                ScheduleConfig.of(360_000, 20_000));

        var processConfigs =
                Set.of(singleRandomNumberProcessConfig, threeRandomNumbersProcessConfig, tenRandomNumbersProcessConfig);

        return PluginConfig.of(IDENTIFIER, processConfigs);
    }

}
