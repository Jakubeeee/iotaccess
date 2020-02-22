package com.jakubeeee.masterthesis.meteoplugin.impl.converter.json;

class CorrectTestJsonFetchedData {

    static final String CORRECT_NO_VALUES_JSON =
            """

            [
            ]
            """;

    static final String CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_JSON =
            """

            [
               {
                  "luminance":"95.0"
               }
            ]
            """;

    static final String CORRECT_SINGLE_FRAGMENT_ALL_VALUES_JSON =
            """

            [
               {
                  "luminance":"95.0",
                  "rain(analog)":"1024.0",
                  "wind power":"0.0",
                  "gps longitude":"24.958134573233315",
                  "temperature":"20.82916666666667",
                  "wind direction":"SW",
                  "humidity":"36.108333333333334",
                  "id":"AMC1582377944234",
                  "pressure":"997.2741666666667",
                  "gps latitude":"41.47672335683144",
                  "gps altitude":"82.0",
                  "rain(digital)":"1.0",
                  "moment":"2020-02-14 08:55:30"
               }
            ]
            """;

    static final String CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_JSON =
            """

            [
               {
                  "luminance":"95.0"
               },
               {
                  "luminance":"18.0"
               },
               {
                  "luminance":"0.0"
               }
            ]
            """;

    static final String CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_JSON =
            """

            [
               {
                  "luminance":"95.0",
                  "rain(analog)":"1024.0",
                  "wind power":"0.0",
                  "gps longitude":"24.958134573233315",
                  "temperature":"21.1",
                  "wind direction":"SW",
                  "humidity":"38.0",
                  "id":"SNG15767123017980008",
                  "pressure":"1000.41",
                  "gps latitude":"41.47672335683144",
                  "gps altitude":"82.0",
                  "rain(digital)":"1.0",
                  "moment":"2020-02-14 08:55:30"
               },
               {
                  "luminance":"18.0",
                  "rain(analog)":"1024.0",
                  "wind power":"0.0",
                  "gps longitude":"24.958134573233315",
                  "temperature":"21.05",
                  "wind direction":"SW",
                  "humidity":"37.8",
                  "id":"SNG15767123017980007",
                  "pressure":"999.33",
                  "gps latitude":"41.47672335683144",
                  "gps altitude":"82.0",
                  "rain(digital)":"1.0",
                  "moment":"2020-02-14 08:55:30"
               },
               {
                  "luminance":"0.0",
                  "rain(analog)":"1024.0",
                  "wind power":"0.0",
                  "gps longitude":"24.958134573233315",
                  "temperature":"20.8",
                  "wind direction":"SW",
                  "humidity":"35.0",
                  "id":"SNG157671230179800011",
                  "pressure":"996.49",
                  "gps latitude":"41.47672335683144",
                  "gps altitude":"82.0",
                  "rain(digital)":"1.0",
                  "moment":"2020-02-14 08:55:30"
               }
            ]
            """;

}
