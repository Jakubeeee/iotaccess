package com.jakubeeee.iotaccess.pluginapi.converter;

import com.jakubeeee.iotaccess.pluginapi.meteo.MeteoPropertyKeyConstants;

/**
 * Constants that represent supported types of data fetched from web services.
 */
public enum DataType {

    /**
     * Standard, generic data that do not fall into any other category
     */
    STANDARD,

    /**
     * Specialized meteorological data with values associated with keys defined in {@link MeteoPropertyKeyConstants}
     */
    METEO

}
