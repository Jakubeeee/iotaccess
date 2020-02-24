package com.jakubeeee.masterthesis.pluginapi.meteo;

import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedProperty;

/**
 * Constants used as keys of {@link FetchedProperty} objects for data of {@link DataType#METEO} type.
 */
public final class MeteoPropertyKeyConstants {

    public static final String IDENTIFIER = "identifier";

    public static final String TEMPERATURE = "temperature";

    public static final String HUMIDITY = "humidity";

    public static final String PRESSURE = "pressure";

    public static final String LUMINANCE = "luminance";

    public static final String RAIN_DIGITAL = "rainDigital";

    public static final String RAIN_ANALOG = "rainAnalog";

    public static final String WIND_POWER = "windPower";

    public static final String WIND_DIRECTION = "windDirection";

    public static final String GPS_ALTITUDE = "gpsAltitude";

    public static final String GPS_LONGITUDE = "gpsLongitude";

    public static final String GPS_LATITUDE = "gpsLatitude";

    public static final String MOMENT = "moment";

    private MeteoPropertyKeyConstants() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " class cannot be initialized");
    }

}
