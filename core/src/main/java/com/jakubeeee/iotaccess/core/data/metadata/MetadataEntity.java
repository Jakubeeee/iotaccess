package com.jakubeeee.iotaccess.core.data.metadata;

import com.jakubeeee.iotaccess.core.data.DataEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class MetadataEntity extends DataEntity {

    @Column(name = "IDENTIFIER", unique = true, nullable = false, updatable = false)
    private String identifier;

}
