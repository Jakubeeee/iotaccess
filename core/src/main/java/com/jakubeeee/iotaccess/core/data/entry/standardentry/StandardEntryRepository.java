package com.jakubeeee.iotaccess.core.data.entry.standardentry;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StandardEntryRepository extends CrudRepository<StandardEntry, Long> {

    @Modifying
    @Query("UPDATE StandardEntry e SET e.processMetadata = null WHERE e.processMetadata.id = :processMetadataId")
    void disconnectFromProcessMetadata(long processMetadataId);

}
