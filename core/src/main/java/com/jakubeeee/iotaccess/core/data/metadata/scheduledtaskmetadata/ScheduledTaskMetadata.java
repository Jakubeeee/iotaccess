package com.jakubeeee.iotaccess.core.data.metadata.scheduledtaskmetadata;

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
@Table(name = "SCHEDULED_TASKS_METADATA")
public class ScheduledTaskMetadata extends MetadataEntity {

    @Column(name = "GROUP_IDENTIFIER", nullable = false, updatable = false)
    private String groupIdentifier;

    @Column(name = "INTERVAL", nullable = false, updatable = false)
    private long interval;

    @Column(name = "RUNNING", nullable = false)
    private boolean running;

    @SuppressWarnings("unused") ScheduledTaskMetadata() {
    }

    public ScheduledTaskMetadata(String identifier, String groupIdentifier, long interval) {
        super(identifier);
        this.groupIdentifier = groupIdentifier;
        this.interval = interval;
        this.running = false;
    }

}
