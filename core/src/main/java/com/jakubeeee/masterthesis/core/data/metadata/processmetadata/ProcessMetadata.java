package com.jakubeeee.masterthesis.core.data.metadata.processmetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataEntity;
import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadata;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "PROCESSES_METADATA")
public final class ProcessMetadata extends MetadataEntity {

    @Column(name = "DESCRIPTION", updatable = false)
    private String description;

    @Column(name = "FETCH_URL", nullable = false, updatable = false)
    private String fetchUrl;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PLUGIN_METADATA_ID", nullable = false, updatable = false)
    private PluginMetadata pluginMetadata;

    @SuppressWarnings("unused") ProcessMetadata() {
    }

    public ProcessMetadata(String identifier, String description, String fetchUrl, PluginMetadata pluginMetadata) {
        super(identifier);
        this.description = description;
        this.fetchUrl = fetchUrl;
        this.pluginMetadata = pluginMetadata;
    }

}
