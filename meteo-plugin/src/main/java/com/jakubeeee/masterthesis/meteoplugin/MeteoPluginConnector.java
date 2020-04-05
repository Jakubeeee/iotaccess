package com.jakubeeee.masterthesis.meteoplugin;

import com.jakubeeee.masterthesis.meteoplugin.impl.converter.MeteoConverterProxy;
import com.jakubeeee.masterthesis.meteoplugin.impl.url.CommandUrlParameter;
import com.jakubeeee.masterthesis.meteoplugin.impl.url.ResultUrlParameter;
import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import com.jakubeeee.masterthesis.pluginapi.config.FetchConfig;
import com.jakubeeee.masterthesis.pluginapi.config.PluginConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ProcessConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ScheduleConfig;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;

import java.util.Set;

import static com.jakubeeee.masterthesis.meteoplugin.impl.url.MeteoUrlHelper.getUrl;

public class MeteoPluginConnector implements PluginConnector {

    private static final String IDENTIFIER = "Meteo plugin";

    @Override
    public DataConverter getConverter() {
        return MeteoConverterProxy.getInstance();
    }

    @Override
    public PluginConfig getConfig() {

        var testJsonSumMeteoConfig = ProcessConfig.of(
                "Test json meteo process (SUM)",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.METEO
                ),
                ScheduleConfig.of(180_000));

        var testJsonGetMeteoConfig = ProcessConfig.of(
                "Test json meteo process (GET)",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.METEO
                ),
                ScheduleConfig.of(720_000));

        var testJsonStandardSumMeteoConfig = ProcessConfig.of(
                "Test json meteo process (SUM) standard data",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.STANDARD
                ),
                ScheduleConfig.of(900_000));

        var testXmlSumMeteoConfig = ProcessConfig.of(
                "Test xml meteo process (SUM)",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.XML),
                        DataFormat.XML, DataType.METEO
                ),
                ScheduleConfig.of(180_000));

        var testXmlGetMeteoConfig = ProcessConfig.of(
                "Test xml meteo process (GET)",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.XML),
                        DataFormat.XML, DataType.METEO
                ),
                ScheduleConfig.of(720_000));

        var testXmlStandardSumMeteoConfig = ProcessConfig.of(
                "Test xml meteo process (SUM) standard data",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.XML),
                        DataFormat.XML, DataType.STANDARD
                ),
                ScheduleConfig.of(900_000));

        var testTxtSumMeteoConfig = ProcessConfig.of(
                "Test txt meteo process (SUM)",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.TXT),
                        DataFormat.TXT, DataType.METEO
                ),
                ScheduleConfig.of(180_000));

        var testTxtGetMeteoConfig = ProcessConfig.of(
                "Test txt meteo process (GET)",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.TXT),
                        DataFormat.TXT, DataType.METEO
                ),
                ScheduleConfig.of(720_000));

        var testTxtStandardSumMeteoConfig = ProcessConfig.of(
                "Test txt meteo process (SUM) standard data",
                "",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.TXT),
                        DataFormat.TXT, DataType.STANDARD
                ),
                ScheduleConfig.of(900_000));

        var processConfigs = Set.of(
                testJsonSumMeteoConfig,
                testJsonGetMeteoConfig,
                testJsonStandardSumMeteoConfig,
                testXmlGetMeteoConfig,
                testXmlSumMeteoConfig,
                testXmlStandardSumMeteoConfig,
                testTxtSumMeteoConfig,
                testTxtGetMeteoConfig,
                testTxtStandardSumMeteoConfig
        );

        return PluginConfig.of(IDENTIFIER, processConfigs);
    }

}
