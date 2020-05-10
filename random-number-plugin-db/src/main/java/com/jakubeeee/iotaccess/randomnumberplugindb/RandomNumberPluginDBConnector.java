package com.jakubeeee.iotaccess.randomnumberplugindb;

import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import com.jakubeeee.iotaccess.pluginapi.config.*;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.randomnumberplugindb.impl.RandomNumberConverter;

import java.util.Set;

import static com.jakubeeee.iotaccess.pluginapi.converter.DataFormat.JSON;
import static com.jakubeeee.iotaccess.pluginapi.converter.DataType.STANDARD;
import static com.jakubeeee.iotaccess.randomnumberplugindb.impl.RandomNumberClientConstants.*;

public class RandomNumberPluginDBConnector implements PluginConnector {

    private static final String IDENTIFIER = "Random number plugin (DB)";

    @Override
    public Set<DataConverter> getConverters() {
        return Set.of(RandomNumberConverter.getInstance());
    }

    @Override
    public PluginConfig getConfig() {
        var singleRandomNumberProcessConfig = ProcessConfig.of(
                "Single random number fetch process (DB)",
                "",
                FetchConfig.of(GET_RANDOM_NUMBER_PATH, JSON, STANDARD),
                ConverterConfig.of(RandomNumberConverter.getInstance().getIdentifier()),
                ScheduleConfig.of(30_000));

        var threeRandomNumbersProcessConfig = ProcessConfig.of(
                "Three random numbers fetch process (DB)",
                "",
                FetchConfig.of(GET_3_RANDOM_NUMBERS_PATH, JSON, STANDARD),
                ConverterConfig.of(RandomNumberConverter.getInstance().getIdentifier()),
                ScheduleConfig.of(120_000));

        var tenRandomNumbersProcessConfig = ProcessConfig.of(
                "Ten random numbers fetch process (DB)",
                "",
                FetchConfig.of(GET_10_RANDOM_NUMBERS_PATH, JSON, STANDARD),
                ConverterConfig.of(RandomNumberConverter.getInstance().getIdentifier()),
                ScheduleConfig.of(360_000));

        var processConfigs =
                Set.of(singleRandomNumberProcessConfig, threeRandomNumbersProcessConfig, tenRandomNumbersProcessConfig);

        return PluginConfig.of(IDENTIFIER, processConfigs);
    }

}
