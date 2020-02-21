package com.jakubeeee.masterthesis.meteoplugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.jakubeeee.masterthesis.pluginapi.meteo.MeteoPropertyKeyConstants.*;

public class MeteoXmlConverter implements DataConverter {

    private static final XmlMapper MAPPER = new XmlMapper();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String PARSE_EXCEPTION_MESSAGE =
            "Error during parsing of external data in Random Number Converter.";

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        var records = new ArrayList<FetchedRecord>();
        MeteoXmlContainer[] containers;
        try {
            containers = MAPPER.readValue(rawData, MeteoXmlContainer[].class);
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
        for (var container : containers) {

            String rawDateTime = container.getMoment();
            Instant dateTime = null;
            if (rawDateTime != null)
                dateTime = LocalDateTime.parse(container.getMoment(), FORMATTER).atZone(ZoneId.of("+1")).toInstant();

            var properties = List.of(
                    new FetchedText(IDENTIFIER, container.getIdentifier()),
                    new FetchedNumber(TEMPERATURE, container.getTemperature()),
                    new FetchedNumber(HUMIDITY, container.getHumidity()),
                    new FetchedNumber(PRESSURE, container.getPressure()),
                    new FetchedNumber(LUMINANCE, container.getLuminance()),
                    new FetchedNumber(RAIN_DIGITAL, container.getRainDigital()),
                    new FetchedNumber(RAIN_ANALOG, container.getRainAnalog()),
                    new FetchedNumber(WIND_POWER, container.getWindPower()),
                    new FetchedText(WIND_DIRECTION, container.getWindDirection()),
                    new FetchedNumber(GPS_ALTITUDE, container.getGpsAltitude()),
                    new FetchedNumber(GPS_LONGITUDE, container.getGpsLongitude()),
                    new FetchedNumber(GPS_LATITUDE, container.getGpsLatitude()),
                    new FetchedDate(DATE_TIME, dateTime)
            );
            records.add(FetchedRecord.of(properties));
        }
        return FetchedContainer.of(records);
    }

    private ExternalDataParseException getParseException(Throwable cause) {
        return new ExternalDataParseException(PARSE_EXCEPTION_MESSAGE + " Details in underlying exception message.",
                cause);
    }

}
