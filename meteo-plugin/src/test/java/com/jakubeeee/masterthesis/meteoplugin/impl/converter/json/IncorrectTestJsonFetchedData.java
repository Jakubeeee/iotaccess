package com.jakubeeee.masterthesis.meteoplugin.impl.converter.json;

class IncorrectTestJsonFetchedData {

    static final String INCORRECT_SINGLE_VALUE_MALFORMED_JSON =
            """

            [
               {
                  "luminance""20.82916666666667"
            ]
            """;

    static final String INCORRECT_MULTIPLE_VALUE_MALFORMED_JSON =
            """

            [
               {
                  "luminance":"20.82916666666667"
                  "rain(analog)":"1024.0",
                  "wind power":"0.0",
                  "gps longitude":"24.958134573233315",
                  "temperature""20.82916666666667",
                  "wind direction":"SW",
                  "humidity":"36.108333333333334",
                  "id:"AMC1582377944234",
                  "pressure":"997.2741666666667"
                  "gps latitude":"41.47672335683144",
                  "gps altitude":"82.0",
                  "rain(digital)"1.0",
                  "moment":"2020-02-14 08:55:30"
               }
            """;

}
