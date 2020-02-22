package com.jakubeeee.masterthesis.meteoplugin.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedRecord;
import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.jakubeeee.masterthesis.meteoplugin.converter.FetchedPropertyHelper.*;
import static com.jakubeeee.masterthesis.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.util.Collections.unmodifiableList;

final class MeteoJsonConverter implements DataConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String PARSE_EXCEPTION_MESSAGE =
            "Error during parsing of external data in Meteo Json Converter.";

    private static final MeteoJsonConverter INSTANCE = new MeteoJsonConverter();

    private MeteoJsonConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        var records = new ArrayList<FetchedRecord>();
        MeteoJsonExternalContainer[] containers;
        try {
            containers = MAPPER.readValue(rawData, MeteoJsonExternalContainer[].class);
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
        for (var container : containers) {

            String rawDateTime = container.getMoment();
            Instant moment = null;
            if (rawDateTime != null)
                moment = LocalDateTime.parse(container.getMoment(), FORMATTER).atZone(ZoneId.of("+1")).toInstant();

            var properties = List.of(
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
                    createFetchedDate(MOMENT, moment)
            );
            records.add(FetchedRecord.of(properties));
        }
        return FetchedContainer.of(unmodifiableList(records));
    }

    private ExternalDataParseException getParseException(Throwable cause) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " Details in underlying exception message.",
                cause);
    }

    public static MeteoJsonConverter getInstance() {
        return INSTANCE;
    }

}
