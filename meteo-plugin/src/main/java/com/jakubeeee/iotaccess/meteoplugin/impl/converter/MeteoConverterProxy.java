package com.jakubeeee.iotaccess.meteoplugin.impl.converter;

import com.jakubeeee.iotaccess.meteoplugin.impl.converter.json.MeteoJsonConverter;
import com.jakubeeee.iotaccess.meteoplugin.impl.converter.txt.MeteoTxtConverter;
import com.jakubeeee.iotaccess.meteoplugin.impl.converter.xml.MeteoXmlConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import lombok.NonNull;

public final class MeteoConverterProxy implements DataConverter {

    private static final MeteoConverterProxy INSTANCE = new MeteoConverterProxy();

    private MeteoConverterProxy() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        return switch (dataFormat) {
            case JSON -> MeteoJsonConverter.getInstance().convert(rawData, dataFormat);
            case XML -> MeteoXmlConverter.getInstance().convert(rawData, dataFormat);
            case TXT -> MeteoTxtConverter.getInstance().convert(rawData, dataFormat);
        };
    }

    public static MeteoConverterProxy getInstance() {
        return INSTANCE;
    }

}

