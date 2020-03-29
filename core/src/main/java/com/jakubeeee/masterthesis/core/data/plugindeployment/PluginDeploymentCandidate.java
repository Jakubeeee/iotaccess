package com.jakubeeee.masterthesis.core.data.plugindeployment;

import com.jakubeeee.masterthesis.core.data.DataEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "PLUGIN_DEPLOYMENTS")
public final class PluginDeploymentCandidate extends DataEntity {

    @Column(name = "JAR_NAME", unique = true, nullable = false, updatable = false)
    private String jarName;

    @Column(name = "DEPLOYED", nullable = false)
    private boolean deployed;

    @Lob
    @Column(name = "BINARY_DATA", nullable = false, updatable = false)
    private byte[] binaryData;

}
