package com.jakubeeee.masterthesis.core.data.entry.standardentry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
final class KeysContainer {

    @Column(name = "TEXT_KEY_1", updatable = false)
    private String textKey1;

    @Column(name = "TEXT_KEY_2", updatable = false)
    private String textKey2;

    @Column(name = "TEXT_KEY_3", updatable = false)
    private String textKey3;

    @Column(name = "TEXT_KEY_4", updatable = false)
    private String textKey4;

    @Column(name = "TEXT_KEY_5", updatable = false)
    private String textKey5;

    @Column(name = "NUMBER_KEY_1", updatable = false)
    private String numberKey1;

    @Column(name = "NUMBER_KEY_2", updatable = false)
    private String numberKey2;

    @Column(name = "NUMBER_KEY_3", updatable = false)
    private String numberKey3;

    @Column(name = "NUMBER_KEY_4", updatable = false)
    private String numberKey4;

    @Column(name = "NUMBER_KEY_5", updatable = false)
    private String numberKey5;

    @Column(name = "NUMBER_KEY_6", updatable = false)
    private String numberKey6;

    @Column(name = "NUMBER_KEY_7", updatable = false)
    private String numberKey7;

    @Column(name = "NUMBER_KEY_8", updatable = false)
    private String numberKey8;

    @Column(name = "NUMBER_KEY_9", updatable = false)
    private String numberKey9;

    @Column(name = "NUMBER_KEY_10", updatable = false)
    private String numberKey10;

    @Column(name = "DATE_KEY_1", updatable = false)
    private String dateKey1;

    @Column(name = "DATE_KEY_2", updatable = false)
    private String dateKey2;

    @Column(name = "DATE_KEY_3", updatable = false)
    private String dateKey3;

    @Column(name = "DATE_KEY_4", updatable = false)
    private String dateKey4;

    @Column(name = "DATE_KEY_5", updatable = false)
    private String dateKey5;

}
