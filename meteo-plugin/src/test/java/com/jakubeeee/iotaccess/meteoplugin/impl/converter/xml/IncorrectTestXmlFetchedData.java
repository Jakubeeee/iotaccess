package com.jakubeeee.iotaccess.meteoplugin.impl.converter.xml;

class IncorrectTestXmlFetchedData {

    static final String INCORRECT_SINGLE_VALUE_MALFORMED_XML =
            """

            <METEO_PLACES>
               <METEO_DATA
                  <LUMINANCE>95.0</LUMINANCE>
            </METEO_PLACES>
            """;

    static final String INCORRECT_MULTIPLE_VALUE_MALFORMED_XML =
            """

            <METEO_PLACES>
               <METEO_DATA>
                  <LUMINANCE>95.0</LUMINANCE>
                  <RAIN_ANALOG>1024.0</RAIN_ANALOG>
                  <WIND_POWER>0.0</WIND_POWER>
                  <GPS_LONGITUDE>24.958134573233315</GPS_LONGITUDE>
                  <TEMPERATURE20.82916666666667</TEMPERATURE
                  <WIND_DIRECTION>SW</WIND_DIRECTION>
                  <HUMIDITY>36.108333333333334</HUMIDITY>
                  <ID>AMC1582377944234<ID>
                  <PRESSURE>997.2741666666667</PRESSURE>
                  <GPS_LATITUDE>41.47672335683144</GPS_LATITUDE>
                  <GPS_ALTITUDE>82.0/GPS_ALTITUDE>
                  <RAIN_DIGITAL>1.0</RAIN_DIGITAL>
                  <MOMENT>2020-02-14 08:55:30</MOMENT>
               </METEO_DATA>
            """;

}
