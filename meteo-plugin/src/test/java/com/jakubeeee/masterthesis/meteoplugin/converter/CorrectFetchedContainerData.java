package com.jakubeeee.masterthesis.meteoplugin.converter;

import com.jakubeeee.masterthesis.pluginapi.property.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class CorrectFetchedContainerData {

    static final FetchedContainer EMPTY_CONTAINER = container();

    static final FetchedContainer CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_CONTAINER;

    static {
        CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_CONTAINER =
                container(
                        new FetchedNullText("identifier"),
                        new FetchedNullNumber("temperature"),
                        new FetchedNullNumber("humidity"),
                        new FetchedNullNumber("pressure"),
                        new FetchedNumber("luminance", new BigDecimal("95.0")),
                        new FetchedNullNumber("rainDigital"),
                        new FetchedNullNumber("rainAnalog"),
                        new FetchedNullNumber("windPower"),
                        new FetchedNullText("windDirection"),
                        new FetchedNullNumber("gpsAltitude"),
                        new FetchedNullNumber("gpsLongitude"),
                        new FetchedNullNumber("gpsLatitude"),
                        new FetchedNullDate("moment")
                );
    }

    static final FetchedContainer CORRECT_SINGLE_FRAGMENT_ALL_VALUES_CONTAINER;

    static {
        CORRECT_SINGLE_FRAGMENT_ALL_VALUES_CONTAINER =
                container(
                        new FetchedText("identifier", "AMC1582377944234"),
                        new FetchedNumber("temperature", new BigDecimal("20.82916666666667")),
                        new FetchedNumber("humidity", new BigDecimal("36.108333333333334")),
                        new FetchedNumber("pressure", new BigDecimal("997.2741666666667")),
                        new FetchedNumber("luminance", new BigDecimal("95.0")),
                        new FetchedNumber("rainDigital", new BigDecimal("1.0")),
                        new FetchedNumber("rainAnalog", new BigDecimal("1024.0")),
                        new FetchedNumber("windPower", new BigDecimal("0.0")),
                        new FetchedText("windDirection", "SW"),
                        new FetchedNumber("gpsAltitude", new BigDecimal("82.0")),
                        new FetchedNumber("gpsLongitude", new BigDecimal("24.958134573233315")),
                        new FetchedNumber("gpsLatitude", new BigDecimal("41.47672335683144")),
                        new FetchedDate("moment", Instant.parse("2020-02-14T07:55:30Z"))
                );
    }

    static final FetchedContainer CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_CONTAINER;

    static {
        CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_CONTAINER =
                container(
                        FetchedRecord.of(
                                List.of(
                                        new FetchedNullText("identifier"),
                                        new FetchedNullNumber("temperature"),
                                        new FetchedNullNumber("humidity"),
                                        new FetchedNullNumber("pressure"),
                                        new FetchedNumber("luminance", new BigDecimal("95.0")),
                                        new FetchedNullNumber("rainDigital"),
                                        new FetchedNullNumber("rainAnalog"),
                                        new FetchedNullNumber("windPower"),
                                        new FetchedNullText("windDirection"),
                                        new FetchedNullNumber("gpsAltitude"),
                                        new FetchedNullNumber("gpsLongitude"),
                                        new FetchedNullNumber("gpsLatitude"),
                                        new FetchedNullDate("moment")
                                )
                        ),
                        FetchedRecord.of(
                                List.of(
                                        new FetchedNullText("identifier"),
                                        new FetchedNullNumber("temperature"),
                                        new FetchedNullNumber("humidity"),
                                        new FetchedNullNumber("pressure"),
                                        new FetchedNumber("luminance", new BigDecimal("18.0")),
                                        new FetchedNullNumber("rainDigital"),
                                        new FetchedNullNumber("rainAnalog"),
                                        new FetchedNullNumber("windPower"),
                                        new FetchedNullText("windDirection"),
                                        new FetchedNullNumber("gpsAltitude"),
                                        new FetchedNullNumber("gpsLongitude"),
                                        new FetchedNullNumber("gpsLatitude"),
                                        new FetchedNullDate("moment")
                                )
                        ),
                        FetchedRecord.of(
                                List.of(
                                        new FetchedNullText("identifier"),
                                        new FetchedNullNumber("temperature"),
                                        new FetchedNullNumber("humidity"),
                                        new FetchedNullNumber("pressure"),
                                        new FetchedNumber("luminance", new BigDecimal("0.0")),
                                        new FetchedNullNumber("rainDigital"),
                                        new FetchedNullNumber("rainAnalog"),
                                        new FetchedNullNumber("windPower"),
                                        new FetchedNullText("windDirection"),
                                        new FetchedNullNumber("gpsAltitude"),
                                        new FetchedNullNumber("gpsLongitude"),
                                        new FetchedNullNumber("gpsLatitude"),
                                        new FetchedNullDate("moment")
                                )
                        )
                );
    }

    static final FetchedContainer CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_CONTAINER;

    static {
        CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_CONTAINER =
                container(
                        FetchedRecord.of(
                                List.of(
                                        new FetchedText("identifier", "SNG15767123017980008"),
                                        new FetchedNumber("temperature", new BigDecimal("21.1")),
                                        new FetchedNumber("humidity", new BigDecimal("38.0")),
                                        new FetchedNumber("pressure", new BigDecimal("1000.41")),
                                        new FetchedNumber("luminance", new BigDecimal("95.0")),
                                        new FetchedNumber("rainDigital", new BigDecimal("1.0")),
                                        new FetchedNumber("rainAnalog", new BigDecimal("1024.0")),
                                        new FetchedNumber("windPower", new BigDecimal("0.0")),
                                        new FetchedText("windDirection", "SW"),
                                        new FetchedNumber("gpsAltitude", new BigDecimal("82.0")),
                                        new FetchedNumber("gpsLongitude", new BigDecimal("24.958134573233315")),
                                        new FetchedNumber("gpsLatitude", new BigDecimal("41.47672335683144")),
                                        new FetchedDate("moment", Instant.parse("2020-02-14T07:55:30Z"))
                                )
                        ),
                        FetchedRecord.of(
                                List.of(
                                        new FetchedText("identifier", "SNG15767123017980007"),
                                        new FetchedNumber("temperature", new BigDecimal("21.05")),
                                        new FetchedNumber("humidity", new BigDecimal("37.8")),
                                        new FetchedNumber("pressure", new BigDecimal("999.33")),
                                        new FetchedNumber("luminance", new BigDecimal("18.0")),
                                        new FetchedNumber("rainDigital", new BigDecimal("1.0")),
                                        new FetchedNumber("rainAnalog", new BigDecimal("1024.0")),
                                        new FetchedNumber("windPower", new BigDecimal("0.0")),
                                        new FetchedText("windDirection", "SW"),
                                        new FetchedNumber("gpsAltitude", new BigDecimal("82.0")),
                                        new FetchedNumber("gpsLongitude", new BigDecimal("24.958134573233315")),
                                        new FetchedNumber("gpsLatitude", new BigDecimal("41.47672335683144")),
                                        new FetchedDate("moment", Instant.parse("2020-02-14T07:55:30Z"))
                                )
                        ),
                        FetchedRecord.of(
                                List.of(
                                        new FetchedText("identifier", "SNG157671230179800011"),
                                        new FetchedNumber("temperature", new BigDecimal("20.8")),
                                        new FetchedNumber("humidity", new BigDecimal("35.0")),
                                        new FetchedNumber("pressure", new BigDecimal("996.49")),
                                        new FetchedNumber("luminance", new BigDecimal("0.0")),
                                        new FetchedNumber("rainDigital", new BigDecimal("1.0")),
                                        new FetchedNumber("rainAnalog", new BigDecimal("1024.0")),
                                        new FetchedNumber("windPower", new BigDecimal("0.0")),
                                        new FetchedText("windDirection", "SW"),
                                        new FetchedNumber("gpsAltitude", new BigDecimal("82.0")),
                                        new FetchedNumber("gpsLongitude", new BigDecimal("24.958134573233315")),
                                        new FetchedNumber("gpsLatitude", new BigDecimal("41.47672335683144")),
                                        new FetchedDate("moment", Instant.parse("2020-02-14T07:55:30Z"))
                                )
                        )
                );
    }

    private static FetchedContainer container() {
        return FetchedContainer.of(emptyList());
    }

    private static FetchedContainer container(FetchedRecord... records) {
        return FetchedContainer.of(List.of(records));
    }

    private static FetchedContainer container(FetchedProperty<?>... properties) {
        return FetchedContainer.of(singletonList(FetchedRecord.of(List.of(properties))));
    }

}
