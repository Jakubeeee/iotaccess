package com.jakubeeee.masterthesis.meteoplugin.impl.converter.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
final class MeteoJsonExternalContainer {

    @JsonProperty(value = "id")
    private String identifier;

    @JsonProperty(value = "temperature")
    private BigDecimal temperature;

    @JsonProperty(value = "humidity")
    private BigDecimal humidity;

    @JsonProperty(value = "pressure")
    private BigDecimal pressure;

    @JsonProperty(value = "luminance")
    private BigDecimal luminance;

    @JsonProperty(value = "rain(digital)")
    private BigDecimal rainDigital;

    @JsonProperty(value = "rain(analog)")
    private BigDecimal rainAnalog;

    @JsonProperty(value = "wind power")
    private BigDecimal windPower;

    @JsonProperty(value = "wind direction")
    private String windDirection;

    @JsonProperty(value = "gps altitude")
    private BigDecimal gpsAltitude;

    @JsonProperty(value = "gps longitude")
    private BigDecimal gpsLongitude;

    @JsonProperty(value = "gps latitude")
    private BigDecimal gpsLatitude;

    @JacksonXmlProperty(localName = "moment")
    private String moment;

}
