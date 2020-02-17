package com.jakubeeee.masterthesis.core.data.entry;

import com.jakubeeee.masterthesis.core.data.DataEntity;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@MappedSuperclass
public abstract class EntryEntity extends DataEntity {

    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private Instant creationDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PROCESS_METADATA_ID", nullable = false, updatable = false)
    private ProcessMetadata processMetadata;

    public EntryEntity(ProcessMetadata processMetadata) {
        this.creationDate = Instant.now();
        this.processMetadata = processMetadata;
    }

}
