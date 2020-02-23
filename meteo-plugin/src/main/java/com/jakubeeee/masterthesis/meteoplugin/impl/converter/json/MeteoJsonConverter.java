package com.jakubeeee.masterthesis.meteoplugin.impl.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedRecord;
import lombok.NonNull;

import java.util.List;

import static com.jakubeeee.masterthesis.meteoplugin.impl.converter.MeteoConverterHelper.*;
import static com.jakubeeee.masterthesis.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toUnmodifiableList;

public final class MeteoJsonConverter implements DataConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final MeteoJsonConverter INSTANCE = new MeteoJsonConverter();

    private MeteoJsonConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        MeteoJsonExternalContainer[] containers = getExternalContainers(rawData);
        List<FetchedRecord> records = createRecords(containers);
        return FetchedContainer.of(records);
    }

    private MeteoJsonExternalContainer[] getExternalContainers(String rawData) {
        try {
            return MAPPER.readValue(rawData, MeteoJsonExternalContainer[].class);
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
    }

    private List<FetchedRecord> createRecords(MeteoJsonExternalContainer[] containers) {
        return stream(containers)
                .map(this::createRecord)
                .collect(toUnmodifiableList());
    }

    private FetchedRecord createRecord(MeteoJsonExternalContainer container) {
        return FetchedRecord.of(List.of(
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

    public static MeteoJsonConverter getInstance() {
        return INSTANCE;
    }

}
