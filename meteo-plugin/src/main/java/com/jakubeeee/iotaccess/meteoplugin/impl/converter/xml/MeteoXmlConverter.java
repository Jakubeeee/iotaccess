package com.jakubeeee.iotaccess.meteoplugin.impl.converter.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedVector;
import lombok.NonNull;

import java.util.List;

import static com.jakubeeee.iotaccess.meteoplugin.impl.converter.MeteoConverterHelper.*;
import static com.jakubeeee.iotaccess.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.util.stream.Collectors.toUnmodifiableList;

public final class MeteoXmlConverter implements DataConverter {

    private static final XmlMapper MAPPER = new XmlMapper();

    private static final MeteoXmlConverter INSTANCE = new MeteoXmlConverter();

    private MeteoXmlConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {
        MeteoXmlExternalContainerWrapper wrapper = getExternalContainerWrapper(rawData);
        List<FetchedVector> vectors = createVectors(wrapper);
        return FetchedContainer.of(vectors);
    }

    private MeteoXmlExternalContainerWrapper getExternalContainerWrapper(String rawData) {
        try {
            return MAPPER.readValue(rawData, MeteoXmlExternalContainerWrapper.class);
        } catch (JsonProcessingException e) {
            throw getParseException(e);
        }
    }

    private List<FetchedVector> createVectors(MeteoXmlExternalContainerWrapper wrapper) {
        return wrapper.getContainers().stream()
                .map(this::createVector)
                .collect(toUnmodifiableList());
    }

    private FetchedVector createVector(MeteoXmlExternalContainer container) {
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

    public static MeteoXmlConverter getInstance() {
        return INSTANCE;
    }

}
