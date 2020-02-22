package com.jakubeeee.masterthesis.meteoplugin;

import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.property.*;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jakubeeee.masterthesis.pluginapi.meteo.MeteoPropertyKeyConstants.*;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class MeteoTxtConverter implements DataConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final MeteoTxtConverter INSTANCE = new MeteoTxtConverter();

    private MeteoTxtConverter() {
    }

    @Override
    public FetchedContainer convert(@NonNull String rawData, @NonNull DataFormat dataFormat) {

        List<FetchedRecord> records = new ArrayList<>();
        String[] fragments = rawData.split("\n\\s*\n");

        for (var fragment : fragments) {

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
                    new FetchedText(IDENTIFIER, map.get("id")),
                    new FetchedNumber(TEMPERATURE, new BigDecimal(map.get("temperature"))),
                    new FetchedNumber(HUMIDITY, new BigDecimal(map.get("humidity"))),
                    new FetchedNumber(PRESSURE, new BigDecimal(map.get("pressure"))),
                    new FetchedNumber(LUMINANCE, new BigDecimal(map.get("luminance"))),
                    new FetchedNumber(RAIN_DIGITAL, new BigDecimal(map.get("rain(digital)"))),
                    new FetchedNumber(RAIN_ANALOG, new BigDecimal(map.get("rain(analog)"))),
                    new FetchedNumber(WIND_POWER, new BigDecimal(map.get("wind power"))),
                    new FetchedText(WIND_DIRECTION, map.get("wind direction")),
                    new FetchedNumber(GPS_ALTITUDE,
                            map.get("gps altitude") == null ? null : new BigDecimal(map.get("gps altitude"))),
                    new FetchedNumber(GPS_LONGITUDE,
                            map.get("gps longitude") == null ? null : new BigDecimal(map.get("gps longitude"))),
                    new FetchedNumber(GPS_LATITUDE,
                            map.get("gps latitude") == null ? null : new BigDecimal(map.get("gps latitude"))),
                    new FetchedDate(DATE_TIME, dateTime)
            ));
            records.add(record);
        }
        return FetchedContainer.of(records);
    }

    public static MeteoTxtConverter getInstance() {
        return INSTANCE;
    }

}
