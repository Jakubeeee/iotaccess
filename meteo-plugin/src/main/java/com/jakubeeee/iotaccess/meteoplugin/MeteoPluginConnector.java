package com.jakubeeee.iotaccess.meteoplugin;

import com.jakubeeee.iotaccess.meteoplugin.impl.converter.json.MeteoJsonConverter;
import com.jakubeeee.iotaccess.meteoplugin.impl.converter.txt.MeteoTxtConverter;
import com.jakubeeee.iotaccess.meteoplugin.impl.converter.xml.MeteoXmlConverter;
import com.jakubeeee.iotaccess.meteoplugin.impl.url.CommandUrlParameter;
import com.jakubeeee.iotaccess.meteoplugin.impl.url.ResultUrlParameter;
import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import com.jakubeeee.iotaccess.pluginapi.config.*;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.converter.DataType;

import java.util.Set;

import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.MeteoConverterConstants.*;
import static com.jakubeeee.iotaccess.meteoplugin.impl.url.MeteoUrlHelper.getUrl;

public class MeteoPluginConnector implements PluginConnector {

    private static final String IDENTIFIER = "meteo_plugin";

    @Override
    public Set<DataConverter> getConverters() {
        return Set.of(
                MeteoJsonConverter.getInstance(),
                MeteoXmlConverter.getInstance(),
                MeteoTxtConverter.getInstance());
    }

    @Override
    public PluginConfig getConfig() {

        var testJsonSumMeteoConfig = ProcessConfig.of(
                "test_json_meteo_process_sum",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.METEO
                ),
                ConverterConfig.of(JSON_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(300_000));

        var testJsonGetMeteoConfig = ProcessConfig.of(
                "test_json_meteo_process_get",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.METEO
                ),
                ConverterConfig.of(JSON_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(3_600_000));

        var testJsonStandardSumMeteoConfig = ProcessConfig.of(
                "test_json_meteo_process_sum_standard_data",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.JSON),
                        DataFormat.JSON, DataType.STANDARD
                ),
                ConverterConfig.of(JSON_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(600_000));

        var testXmlSumMeteoConfig = ProcessConfig.of(
                "test_xml_meteo_process_sum",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.XML),
                        DataFormat.XML, DataType.METEO
                ),
                ConverterConfig.of(XML_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(300_000));

        var testXmlGetMeteoConfig = ProcessConfig.of(
                "test_xml_meteo_process_get",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.XML),
                        DataFormat.XML, DataType.METEO
                ),
                ConverterConfig.of(XML_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(3_600_000));

        var testXmlStandardSumMeteoConfig = ProcessConfig.of(
                "test_xml_meteo_process_sum_standard_data",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.XML),
                        DataFormat.XML, DataType.STANDARD
                ),
                ConverterConfig.of(XML_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(600_000));

        var testTxtSumMeteoConfig = ProcessConfig.of(
                "test_txt_meteo_process_sum",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.TXT),
                        DataFormat.TXT, DataType.METEO
                ),
                ConverterConfig.of(TXT_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(300_000));

        var testTxtGetMeteoConfig = ProcessConfig.of(
                "test_txt_meteo_process_get",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.GET, ResultUrlParameter.TXT),
                        DataFormat.TXT, DataType.METEO
                ),
                ConverterConfig.of(TXT_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(3_600_000));

        var testTxtStandardSumMeteoConfig = ProcessConfig.of(
                "test_txt_meteo_process_sum_standard_data",
                FetchConfig.of(
                        getUrl(CommandUrlParameter.SUM, ResultUrlParameter.TXT),
                        DataFormat.TXT, DataType.STANDARD
                ),
                ConverterConfig.of(TXT_CONVERTER_IDENTIFIER),
                ScheduleConfig.of(600_000));

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
