package com.jakubeeee.masterthesis.core.data.metadata.deployermetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataEntity;
import com.jakubeeee.masterthesis.core.plugindeployer.RegistrationStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

/**
 * Represents information about deployers that were properly registered by this application.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "DEPLOYERS_METADATA")
public final class DeployerMetadata extends MetadataEntity {

    @Enumerated(value = STRING)
    @Column(name = "REGISTRATION_STRATEGY", nullable = false, updatable = false)
    private RegistrationStrategy registrationStrategy;

    @Column(name = "DEPLOYED", nullable = false)
    private boolean registered;

    @SuppressWarnings("unused") DeployerMetadata() {
    }

    public DeployerMetadata(String identifier, RegistrationStrategy registrationStrategy) {
        super(identifier);
        this.registrationStrategy = registrationStrategy;
        this.registered = false;
    }

}
