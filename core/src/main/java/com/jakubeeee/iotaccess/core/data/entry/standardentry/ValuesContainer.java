package com.jakubeeee.iotaccess.core.data.entry.standardentry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
final class ValuesContainer {

    @Column(name = "TEXT_1", updatable = false)
    private String text1;

    @Column(name = "TEXT_2", updatable = false)
    private String text2;

    @Column(name = "TEXT_3", updatable = false)
    private String text3;

    @Column(name = "TEXT_4", updatable = false)
    private String text4;

    @Column(name = "TEXT_5", updatable = false)
    private String text5;

    @Column(name = "NUMBER_1", updatable = false)
    private BigDecimal number1;

    @Column(name = "NUMBER_2", updatable = false)
    private BigDecimal number2;

    @Column(name = "NUMBER_3", updatable = false)
    private BigDecimal number3;

    @Column(name = "NUMBER_4", updatable = false)
    private BigDecimal number4;

    @Column(name = "NUMBER_5", updatable = false)
    private BigDecimal number5;

    @Column(name = "NUMBER_6", updatable = false)
    private BigDecimal number6;

    @Column(name = "NUMBER_7", updatable = false)
    private BigDecimal number7;

    @Column(name = "NUMBER_8", updatable = false)
    private BigDecimal number8;

    @Column(name = "NUMBER_9", updatable = false)
    private BigDecimal number9;

    @Column(name = "NUMBER_10", updatable = false)
    private BigDecimal number10;

    @Column(name = "DATE_1", updatable = false)
    private Instant date1;

    @Column(name = "DATE_2", updatable = false)
    private Instant date2;

    @Column(name = "DATE_3", updatable = false)
    private Instant date3;

    @Column(name = "DATE_4", updatable = false)
    private Instant date4;

    @Column(name = "DATE_5", updatable = false)
    private Instant date5;

}
