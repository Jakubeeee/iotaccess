package com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents information about plugins that were properly registered by this application..
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "PLUGINS_METADATA")
public final class PluginMetadata extends MetadataEntity {

    @Column(name = "DEPLOYED", nullable = false)
    private boolean deployed;

    @SuppressWarnings("unused") PluginMetadata() {
    }

    public PluginMetadata(String identifier) {
        super(identifier);
        this.deployed = false;
    }

}
