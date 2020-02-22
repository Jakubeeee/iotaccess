package com.jakubeeee.masterthesis.meteoplugin;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
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

