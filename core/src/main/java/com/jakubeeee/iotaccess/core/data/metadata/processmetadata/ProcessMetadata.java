package com.jakubeeee.iotaccess.core.data.metadata.processmetadata;

import com.jakubeeee.iotaccess.core.data.metadata.MetadataEntity;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadata;
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

    @Column(name = "INTERVAL", nullable = false, updatable = false)
    private long interval;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PLUGIN_METADATA_ID", nullable = false, updatable = false)
    private PluginMetadata pluginMetadata;

    @SuppressWarnings("unused") ProcessMetadata() {
    }

    public ProcessMetadata(String identifier, String description, String fetchUrl, long interval,
                           PluginMetadata pluginMetadata) {
        super(identifier);
        this.description = description;
        this.fetchUrl = fetchUrl;
        this.interval = interval;
        this.pluginMetadata = pluginMetadata;
    }

}
