package com.jakubeeee.masterthesis.core.data.entry.standardentry;

import com.jakubeeee.masterthesis.core.data.entry.EntryEntity;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadata;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "STANDARD_ENTRIES")
public final class StandardEntry extends EntryEntity {

    @Delegate
    @Embedded
    private KeysContainer keysContainer;

    @Delegate
    @Embedded
    private ValuesContainer valuesContainer;

    @SuppressWarnings("unused") StandardEntry() {
    }

    public StandardEntry(ProcessMetadata processMetadata) {
        super(processMetadata);
        this.keysContainer = new KeysContainer();
        this.valuesContainer = new ValuesContainer();
    }

}
