package com.jakubeeee.masterthesis.meteoplugin.impl.converter.txt;

final class MeteoTxtConverterConstants {

    static final String ID_KEY = "id";

    static final String TEMPERATURE_KEY = "temperature";

    static final String HUMIDITY_KEY = "humidity";

    static final String PRESSURE_KEY = "pressure";

    static final String LUMINANCE_KEY = "luminance";

    static final String RAIN_DIGITAL_KEY = "rain(digital)";

    static final String RAIN_ANALOG_KEY = "rain(analog)";

    static final String WIND_POWER_KEY = "wind power";

    static final String WIND_DIRECTION_KEY = "wind direction";

    static final String GPS_ALTITUDE_KEY = "gps altitude";

    static final String GPS_LONGITUDE_KEY = "gps longitude";

    static final String GPS_LATITUDE_KEY = "gps latitude";

    static final String MOMENT_KEY = "date/time";

    private MeteoTxtConverterConstants() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
