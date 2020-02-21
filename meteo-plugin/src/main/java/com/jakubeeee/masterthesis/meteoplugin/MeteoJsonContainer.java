package com.jakubeeee.masterthesis.meteoplugin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
final class MeteoJsonContainer {

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

    @JsonProperty(value = "date/time")
    private String moment;

}
