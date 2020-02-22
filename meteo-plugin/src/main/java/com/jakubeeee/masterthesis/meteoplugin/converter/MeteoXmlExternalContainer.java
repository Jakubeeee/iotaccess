package com.jakubeeee.masterthesis.meteoplugin.converter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
final class MeteoXmlExternalContainer {

    @JacksonXmlProperty(localName = "ID")
    private String identifier;

    @JacksonXmlProperty(localName = "TEMPERATURE")
    private BigDecimal temperature;

    @JacksonXmlProperty(localName = "HUMIDITY")
    private BigDecimal humidity;

    @JacksonXmlProperty(localName = "PRESSURE")
    private BigDecimal pressure;

    @JacksonXmlProperty(localName = "LUMINANCE")
    private BigDecimal luminance;

    @JacksonXmlProperty(localName = "RAIN_DIGITAL")
    private BigDecimal rainDigital;

    @JacksonXmlProperty(localName = "RAIN_ANALOG")
    private BigDecimal rainAnalog;

    @JacksonXmlProperty(localName = "WIND_POWER")
    private BigDecimal windPower;

    @JacksonXmlProperty(localName = "WIND_DIRECTION")
    private String windDirection;

    @JacksonXmlProperty(localName = "GPS_ALTITUDE")
    private BigDecimal gpsAltitude;

    @JacksonXmlProperty(localName = "GPS_LONGITUDE")
    private BigDecimal gpsLongitude;

    @JacksonXmlProperty(localName = "GPS_LATITUDE")
    private BigDecimal gpsLatitude;

    @JacksonXmlProperty(localName = "MOMENT")
    private String moment;

}
