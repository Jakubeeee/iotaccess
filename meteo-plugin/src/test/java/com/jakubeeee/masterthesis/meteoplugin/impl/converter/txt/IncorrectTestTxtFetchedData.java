package com.jakubeeee.masterthesis.meteoplugin.impl.converter.txt;

class IncorrectTestTxtFetchedData {

    static final String INCORRECT_SINGLE_VALUE_MALFORMED_TXT =
            """

            Current meteo conditions:
              luminance95.0
            """;

    static final String INCORRECT_MULTIPLE_VALUE_MALFORMED_TXT =
            """

            Current meteo conditions:
              id AMC1582377944234
              temperature20.82916666666667
              humidity=36.108333333333334
              pressure=997.2741666666667
              luminance95.0
              rain(digital)=1.0
              rain(analog)=1024.0
              wind power0.0
              wind direction=SW
              gps altitude=82.0
              gps longitude=24.958134573233315
              gps latitude41.47672335683144
              date/time=2020-02-14 08:55:30
            """;

}
