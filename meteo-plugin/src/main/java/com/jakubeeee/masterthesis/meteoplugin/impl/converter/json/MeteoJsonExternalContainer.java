package com.jakubeeee.masterthesis.meteoplugin.impl.converter.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
final class MeteoJsonExternalContainer {

    @JsonProperty(value = "id")
    private String identifier;

    @JsonProperty(value = "temperature")
    private String temperature;

    @JsonProperty(value = "humidity")
    private String humidity;

    @JsonProperty(value = "pressure")
    private String pressure;

    @JsonProperty(value = "luminance")
    private String luminance;

    @JsonProperty(value = "rain(digital)")
    private String rainDigital;

    @JsonProperty(value = "rain(analog)")
    private String rainAnalog;

    @JsonProperty(value = "wind power")
    private String windPower;

    @JsonProperty(value = "wind direction")
    private String windDirection;

    @JsonProperty(value = "gps altitude")
    private String gpsAltitude;

    @JsonProperty(value = "gps longitude")
    private String gpsLongitude;

    @JsonProperty(value = "gps latitude")
    private String gpsLatitude;

    @JacksonXmlProperty(localName = "moment")
    private String moment;

}
