package com.jakubeeee.iotaccess.core.data.entry.standardentry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StandardEntryRepository extends CrudRepository<StandardEntry, Long> {

}
