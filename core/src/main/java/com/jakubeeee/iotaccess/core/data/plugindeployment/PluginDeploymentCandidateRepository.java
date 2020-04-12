package com.jakubeeee.iotaccess.core.data.plugindeployment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface PluginDeploymentCandidateRepository extends CrudRepository<PluginDeploymentCandidate, Long> {

    Set<PluginDeploymentCandidate> findAllByDeployedFalse();

    @Modifying
    @Query("UPDATE PluginDeploymentCandidate p SET p.deployed = :deployed WHERE p.jarName = :jarName")
    void updateDeployed(boolean deployed, String jarName);

}
