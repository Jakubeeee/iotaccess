package com.jakubeeee.masterthesis.meteoplugin;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import lombok.NonNull;

public class MeteoConverterProxy implements DataConverter {

    private final MeteoJsonConverter meteoJsonConverter = new MeteoJsonConverter();

    private final MeteoXmlConverter meteoXmlConverter = new MeteoXmlConverter();

    private final MeteoTxtConverter meteoTxtConverter = new MeteoTxtConverter();

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        return switch (dataFormat) {
            case JSON -> meteoJsonConverter.convert(rawData, dataFormat);
            case XML -> meteoXmlConverter.convert(rawData, dataFormat);
            case TXT -> meteoTxtConverter.convert(rawData, dataFormat);
        };
    }

}

