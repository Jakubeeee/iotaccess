package com.jakubeeee.masterthesis.meteoplugin;

import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import com.jakubeeee.masterthesis.pluginapi.config.FetchConfig;
import com.jakubeeee.masterthesis.pluginapi.config.PluginConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ProcessConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ScheduleConfig;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;

import java.util.Set;

import static com.jakubeeee.masterthesis.meteoplugin.MeteoUrlHelper.getUrl;

public class MeteoPluginConnector implements PluginConnector {

    private static final String IDENTIFIER = "Meteo plugin";

    @Override
    public DataConverter getConverter() {
        return new MeteoConverterProxy();
    }

    @Override
    public PluginConfig getConfig() {

        var testSumMeteoConfig = ProcessConfig.of(
                "Test meteo process (SUM)",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.METEO
                ),
                ScheduleConfig.of(180_000, 15_000));

        var testGetMeteoConfig = ProcessConfig.of(
                "Test meteo process (GET)",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.METEO
                ),
                ScheduleConfig.of(720_000, 30_000));

        var testStandardSumMeteoConfig = ProcessConfig.of(
                "Test meteo process (SUM) standard data",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.STANDARD
                ),
                ScheduleConfig.of(900_000, 60_000));

        var processConfigs = Set.of(testSumMeteoConfig, testGetMeteoConfig, testStandardSumMeteoConfig);

        return PluginConfig.of(IDENTIFIER, processConfigs);
    }

}
