package com.jakubeeee.iotaccess.core.data.metadata.deployermetadata;

import com.jakubeeee.iotaccess.core.data.metadata.MetadataEntity;
import com.jakubeeee.iotaccess.core.plugindeployer.RegistrationStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "DEPLOYERS_METADATA")
public final class DeployerMetadata extends MetadataEntity {

    @Enumerated(value = STRING)
    @Column(name = "REGISTRATION_STRATEGY", nullable = false, updatable = false)
    private RegistrationStrategy registrationStrategy;

    @Column(name = "INTERVAL", nullable = false, updatable = false)
    private long interval;

    @Column(name = "DEPLOYED", nullable = false)
    private boolean registered;

    @SuppressWarnings("unused") DeployerMetadata() {
    }

    public DeployerMetadata(String identifier, RegistrationStrategy registrationStrategy, long interval) {
        super(identifier);
        this.registrationStrategy = registrationStrategy;
        this.interval = interval;
        this.registered = false;
    }

}
