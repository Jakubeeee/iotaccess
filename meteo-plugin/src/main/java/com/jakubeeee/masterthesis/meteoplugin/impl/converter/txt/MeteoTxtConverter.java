package com.jakubeeee.masterthesis.meteoplugin.impl.converter.txt;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.ExternalDataParseException;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedRecord;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jakubeeee.masterthesis.meteoplugin.impl.converter.FetchedPropertyHelper.*;
import static com.jakubeeee.masterthesis.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class MeteoTxtConverter implements DataConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final MeteoTxtConverter INSTANCE = new MeteoTxtConverter();

    private MeteoTxtConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {

        List<FetchedRecord> records = new ArrayList<>();

        if (rawData.trim().equals(""))
            return FetchedContainer.of(emptyList());

        String[] fragments = rawData.split("\n\\s*\n");

        for (var fragment : fragments) {

            List<String> fragmentLines = fragment.lines()
                    .filter(line -> !line.trim().equals(""))
                    .filter(line -> !line.trim().equals("Current meteo conditions:"))
                    .collect(Collectors.toUnmodifiableList());

            boolean allLinesCorrect = fragmentLines.stream()
                    .allMatch(line -> line.contains("="));

            if (!allLinesCorrect)
                throw new ExternalDataParseException(format("Text fragment could not be parsed: \"{0}\"", fragment));

            Map<String, String> map = fragment.lines()
                    .filter(line -> line.contains("="))
                    .collect(toUnmodifiableMap(
                            line -> line.substring(0, line.indexOf("=")).trim(),
                            line -> line.substring(line.indexOf("=") + 1).trim())
                    );

            String rawDateTime = map.get("date/time");
            Instant dateTime = null;
            if (rawDateTime != null)
                dateTime = LocalDateTime.parse(rawDateTime, FORMATTER).atZone(ZoneId.of("+1")).toInstant();

            var record = FetchedRecord.of(List.of(
                    createFetchedText(IDENTIFIER, map.get("id")),
                    createFetchedNumber(TEMPERATURE,
                            map.get("temperature") == null ? null : new BigDecimal(map.get("temperature"))),
                    createFetchedNumber(HUMIDITY,
                            map.get("humidity") == null ? null : new BigDecimal(map.get("humidity"))),
                    createFetchedNumber(PRESSURE,
                            map.get("pressure") == null ? null : new BigDecimal(map.get("pressure"))),
                    createFetchedNumber(LUMINANCE,
                            map.get("luminance") == null ? null : new BigDecimal(map.get("luminance"))),
                    createFetchedNumber(RAIN_DIGITAL,
                            map.get("rain(digital)") == null ? null : new BigDecimal(map.get("rain(digital)"))),
                    createFetchedNumber(RAIN_ANALOG,
                            map.get("rain(analog)") == null ? null : new BigDecimal(map.get("rain(analog)"))),
                    createFetchedNumber(WIND_POWER,
                            map.get("wind power") == null ? null : new BigDecimal(map.get("wind power"))),
                    createFetchedText(WIND_DIRECTION, map.get("wind direction")),
                    createFetchedNumber(GPS_ALTITUDE,
                            map.get("gps altitude") == null ? null : new BigDecimal(map.get("gps altitude"))),
                    createFetchedNumber(GPS_LONGITUDE,
                            map.get("gps longitude") == null ? null : new BigDecimal(map.get("gps longitude"))),
                    createFetchedNumber(GPS_LATITUDE,
                            map.get("gps latitude") == null ? null : new BigDecimal(map.get("gps latitude"))),
                    createFetchedDate(MOMENT, dateTime)
            ));
            records.add(record);
        }
        return FetchedContainer.of(records);
    }

    public static MeteoTxtConverter getInstance() {
        return INSTANCE;
    }

}
