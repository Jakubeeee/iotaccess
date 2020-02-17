package com.jakubeeee.masterthesis.meteoplugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MeteoJsonConverter implements DataConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String PARSE_EXCEPTION_MESSAGE =
            "Error during parsing of external data in Random Number Converter.";

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        var records = new ArrayList<FetchedRecord>();
        MeteoJsonContainer[] containers;
        try {
            containers = MAPPER.readValue(rawData, MeteoJsonContainer[].class);
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
        for (var container : containers) {
            var properties = List.of(
                    new FetchedText("identifier", container.getIdentifier()),
                    new FetchedNumber("temperature", container.getTemperature()),
                    new FetchedNumber("humidity", container.getHumidity()),
                    new FetchedNumber("pressure", container.getPressure()),
                    new FetchedNumber("luminance", container.getLuminance()),
                    new FetchedNumber("rainDigital", container.getRainDigital()),
                    new FetchedNumber("rainAnalog", container.getRainAnalog()),
                    new FetchedNumber("windPower", container.getWindPower()),
                    new FetchedText("windDirection", container.getWindDirection()),
                    new FetchedNumber("gpsAltitude", container.getGpsAltitude()),
                    new FetchedNumber("gpsLongitude", container.getGpsLongitude()),
                    new FetchedNumber("gpsLatitude", container.getGpsLatitude()),
                    new FetchedDate("dateTime", container.getDateTime())
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
