package com.jakubeeee.iotaccess.core.data.metadata.dynamicconfiggenerator;

import com.jakubeeee.iotaccess.core.data.metadata.MetadataEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "DYNAMIC_CONFIG_GENERATORS_METADATA")
public class DynamicConfigGeneratorMetadata extends MetadataEntity {

    @Column(name = "DEFAULT_INTERVAL", nullable = false, updatable = false)
    private long interval;

    @Column(name = "DEPLOYED", nullable = false)
    private boolean registered;

    @SuppressWarnings("unused") DynamicConfigGeneratorMetadata() {
    }

    public DynamicConfigGeneratorMetadata(String identifier, long interval) {
        super(identifier);
        this.interval = interval;
        this.registered = false;
    }

}
