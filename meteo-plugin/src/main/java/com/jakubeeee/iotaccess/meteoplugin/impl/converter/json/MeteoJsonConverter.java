package com.jakubeeee.iotaccess.meteoplugin.impl.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedVector;
import lombok.NonNull;

import java.util.List;

import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.MeteoConverterConstants.JSON_CONVERTER_IDENTIFIER;
import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.MeteoConverterHelper.*;
import static com.jakubeeee.iotaccess.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.util.Arrays.stream;

public final class MeteoJsonConverter implements DataConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final MeteoJsonConverter INSTANCE = new MeteoJsonConverter();

    private MeteoJsonConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        MeteoJsonExternalContainer[] containers = getExternalContainers(rawData);
        List<FetchedVector> vectors = createVectors(containers);
        return FetchedContainer.of(vectors);
    }

    private MeteoJsonExternalContainer[] getExternalContainers(String rawData) {
        try {
            return MAPPER.readValue(rawData, MeteoJsonExternalContainer[].class);
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
    }

    private List<FetchedVector> createVectors(MeteoJsonExternalContainer[] containers) {
        return stream(containers)
                .map(this::createVector)
                .toList();
    }

    private FetchedVector createVector(MeteoJsonExternalContainer container) {
        return FetchedVector.of(List.of(
                createFetchedText(IDENTIFIER, container.getIdentifier()),
                createFetchedNumber(TEMPERATURE, container.getTemperature()),
                createFetchedNumber(HUMIDITY, container.getHumidity()),
                createFetchedNumber(PRESSURE, container.getPressure()),
                createFetchedNumber(LUMINANCE, container.getLuminance()),
                createFetchedNumber(RAIN_DIGITAL, container.getRainDigital()),
                createFetchedNumber(RAIN_ANALOG, container.getRainAnalog()),
                createFetchedNumber(WIND_POWER, container.getWindPower()),
                createFetchedText(WIND_DIRECTION, container.getWindDirection()),
                createFetchedNumber(GPS_ALTITUDE, container.getGpsAltitude()),
                createFetchedNumber(GPS_LONGITUDE, container.getGpsLongitude()),
                createFetchedNumber(GPS_LATITUDE, container.getGpsLatitude()),
                createFetchedDate(MOMENT, container.getMoment())
        ));
    }

    @Override
    public String getIdentifier() {
        return JSON_CONVERTER_IDENTIFIER;
    }

    public static MeteoJsonConverter getInstance() {
        return INSTANCE;
    }

}
