package com.jakubeeee.iotaccess.core.data.metadata.deployermetadata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface DeployerMetadataRepository extends CrudRepository<DeployerMetadata, Long> {

    Optional<DeployerMetadata> findByIdentifier(String identifier);

    @Modifying
    @Query("UPDATE DeployerMetadata m SET m.registered = :registered WHERE m.identifier = :identifier")
    void updateRegistered(boolean registered, String identifier);

}