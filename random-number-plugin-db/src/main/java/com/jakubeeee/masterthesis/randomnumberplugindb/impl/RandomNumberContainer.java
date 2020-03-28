package com.jakubeeee.masterthesis.randomnumberplugindb.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
final class RandomNumberContainer {

    @JsonProperty(value = "values")
    private List<BigDecimal> values;

}
