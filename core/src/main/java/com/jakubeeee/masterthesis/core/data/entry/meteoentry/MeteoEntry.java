package com.jakubeeee.masterthesis.core.data.entry.meteoentry;

import com.jakubeeee.masterthesis.core.data.entry.EntryEntity;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "METEO_ENTRIES")
public final class MeteoEntry extends EntryEntity {

    @Column(name = "IDENTIFIER", updatable = false)
    private String identifier;

    @Column(name = "TEMPERATURE", updatable = false)
    private BigDecimal temperature;

    @Column(name = "HUMIDITY", updatable = false)
    private BigDecimal humidity;

    @Column(name = "PRESSURE", updatable = false)
    private BigDecimal pressure;

    @Column(name = "LUMINANCE", updatable = false)
    private BigDecimal luminance;

    @Column(name = "RAIN_DIGITAL", updatable = false)
    private BigDecimal rainDigital;

    @Column(name = "RAIN_ANALOG", updatable = false)
    private BigDecimal rainAnalog;

    @Column(name = "WIND_POWER", updatable = false)
    private BigDecimal windPower;

    @Column(name = "WIND_DIRECTION", updatable = false)
    private String windDirection;

    @Column(name = "GPS_ALTITUDE", updatable = false)
    private BigDecimal gpsAltitude;

    @Column(name = "GPS_LONGITUDE", updatable = false)
    private BigDecimal gpsLongitude;

    @Column(name = "GPS_LATITUDE", updatable = false)
    private BigDecimal gpsLatitude;

    @Column(name = "MOMENT", updatable = false)
    private Instant moment;

    public MeteoEntry(ProcessMetadata processMetadata) {
        super(processMetadata);
    }

}
