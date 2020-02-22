package com.jakubeeee.masterthesis.meteoplugin.converter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "METEO_PLACES")
final class MeteoXmlExternalContainerWrapper {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "METEO_DATA")
    private List<MeteoXmlExternalContainer> containers = new ArrayList<>();

}
