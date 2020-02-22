package com.jakubeeee.masterthesis.meteoplugin.converter;

class CorrectTestXmlFetchedData {

    static final String CORRECT_NO_VALUES_XML =
            """

            <METEO_PLACES/>
            """;

    static final String CORRECT_SINGLE_FRAGMENT_SINGLE_VALUE_XML =
            """

            <METEO_PLACES>
               <METEO_DATA>
                  <LUMINANCE>95.0</LUMINANCE>
               </METEO_DATA>
            </METEO_PLACES>
            """;

    static final String CORRECT_SINGLE_FRAGMENT_ALL_VALUES_XML =
            """

            <METEO_PLACES>
               <METEO_DATA>
                  <LUMINANCE>95.0</LUMINANCE>
                  <RAIN_ANALOG>1024.0</RAIN_ANALOG>
                  <WIND_POWER>0.0</WIND_POWER>
                  <GPS_LONGITUDE>24.958134573233315</GPS_LONGITUDE>
                  <TEMPERATURE>20.82916666666667</TEMPERATURE>
                  <WIND_DIRECTION>SW</WIND_DIRECTION>
                  <HUMIDITY>36.108333333333334</HUMIDITY>
                  <ID>AMC1582377944234</ID>
                  <PRESSURE>997.2741666666667</PRESSURE>
                  <GPS_LATITUDE>41.47672335683144</GPS_LATITUDE>
                  <GPS_ALTITUDE>82.0</GPS_ALTITUDE>
                  <RAIN_DIGITAL>1.0</RAIN_DIGITAL>
                  <MOMENT>2020-02-14 08:55:30</MOMENT>
               </METEO_DATA>
            </METEO_PLACES>
            """;

    static final String CORRECT_MULTIPLE_FRAGMENT_SINGLE_VALUE_XML =
            """

            <METEO_PLACES>
               <METEO_DATA>
                  <LUMINANCE>95.0</LUMINANCE>
               </METEO_DATA>
               <METEO_DATA>
                  <LUMINANCE>18.0</LUMINANCE>
               </METEO_DATA>
               <METEO_DATA>
                  <LUMINANCE>0.0</LUMINANCE>
               </METEO_DATA>
            </METEO_PLACES>
            """;

    static final String CORRECT_MULTIPLE_FRAGMENT_ALL_VALUES_XML =
            """

            <METEO_PLACES>
               <METEO_DATA>
                  <LUMINANCE>95.0</LUMINANCE>
                  <RAIN_ANALOG>1024.0</RAIN_ANALOG>
                  <WIND_POWER>0.0</WIND_POWER>
                  <GPS_LONGITUDE>24.958134573233315</GPS_LONGITUDE>
                  <TEMPERATURE>21.1</TEMPERATURE>
                  <WIND_DIRECTION>SW</WIND_DIRECTION>
                  <HUMIDITY>38.0</HUMIDITY>
                  <ID>SNG15767123017980008</ID>
                  <PRESSURE>1000.41</PRESSURE>
                  <GPS_LATITUDE>41.47672335683144</GPS_LATITUDE>
                  <GPS_ALTITUDE>82.0</GPS_ALTITUDE>
                  <RAIN_DIGITAL>1.0</RAIN_DIGITAL>
                  <MOMENT>2020-02-14 08:55:30</MOMENT>
               </METEO_DATA>,
               <METEO_DATA>
                  <LUMINANCE>18.0</LUMINANCE>
                  <RAIN_ANALOG>1024.0</RAIN_ANALOG>
                  <WIND_POWER>0.0</WIND_POWER>
                  <GPS_LONGITUDE>24.958134573233315</GPS_LONGITUDE>
                  <TEMPERATURE>21.05</TEMPERATURE>
                  <WIND_DIRECTION>SW</WIND_DIRECTION>
                  <HUMIDITY>37.8</HUMIDITY>
                  <ID>SNG15767123017980007</ID>
                  <PRESSURE>999.33</PRESSURE>
                  <GPS_LATITUDE>41.47672335683144</GPS_LATITUDE>
                  <GPS_ALTITUDE>82.0</GPS_ALTITUDE>
                  <RAIN_DIGITAL>1.0</RAIN_DIGITAL>
                  <MOMENT>2020-02-14 08:55:30</MOMENT>
               </METEO_DATA>,
               <METEO_DATA>
                  <LUMINANCE>0.0</LUMINANCE>
                  <RAIN_ANALOG>1024.0</RAIN_ANALOG>
                  <WIND_POWER>0.0</WIND_POWER>
                  <GPS_LONGITUDE>24.958134573233315</GPS_LONGITUDE>
                  <TEMPERATURE>20.8</TEMPERATURE>
                  <WIND_DIRECTION>SW</WIND_DIRECTION>
                  <HUMIDITY>35.0</HUMIDITY>
                  <ID>SNG157671230179800011</ID>
                  <PRESSURE>996.49</PRESSURE>
                  <GPS_LATITUDE>41.47672335683144</GPS_LATITUDE>
                  <GPS_ALTITUDE>82.0</GPS_ALTITUDE>
                  <RAIN_DIGITAL>1.0</RAIN_DIGITAL>
                  <MOMENT>2020-02-14 08:55:30</MOMENT>
               </METEO_DATA>
            </METEO_PLACES>
            """;

}
