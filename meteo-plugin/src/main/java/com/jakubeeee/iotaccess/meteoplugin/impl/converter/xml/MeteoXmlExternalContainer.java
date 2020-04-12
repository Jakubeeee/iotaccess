package com.jakubeeee.iotaccess.meteoplugin.impl.converter.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
final class MeteoXmlExternalContainer {

    @JacksonXmlProperty(localName = "ID")
    private String identifier;

    @JacksonXmlProperty(localName = "TEMPERATURE")
    private String temperature;

    @JacksonXmlProperty(localName = "HUMIDITY")
    private String humidity;

    @JacksonXmlProperty(localName = "PRESSURE")
    private String pressure;

    @JacksonXmlProperty(localName = "LUMINANCE")
    private String luminance;

    @JacksonXmlProperty(localName = "RAIN_DIGITAL")
    private String rainDigital;

    @JacksonXmlProperty(localName = "RAIN_ANALOG")
    private String rainAnalog;

    @JacksonXmlProperty(localName = "WIND_POWER")
    private String windPower;

    @JacksonXmlProperty(localName = "WIND_DIRECTION")
    private String windDirection;

    @JacksonXmlProperty(localName = "GPS_ALTITUDE")
    private String gpsAltitude;

    @JacksonXmlProperty(localName = "GPS_LONGITUDE")
    private String gpsLongitude;

    @JacksonXmlProperty(localName = "GPS_LATITUDE")
    private String gpsLatitude;

    @JacksonXmlProperty(localName = "MOMENT")
    private String moment;

}
