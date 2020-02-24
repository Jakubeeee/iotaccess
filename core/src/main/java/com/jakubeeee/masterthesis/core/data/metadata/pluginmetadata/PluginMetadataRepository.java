package com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PluginMetadataRepository extends CrudRepository<PluginMetadata, Long> {

    Optional<PluginMetadata> findByIdentifier(String identifier);

    @Modifying
    @Query("UPDATE PluginMetadata m SET m.deployed = :deployed WHERE m.identifier = :identifier")
    void updateDeployed(boolean deployed, String identifier);

}