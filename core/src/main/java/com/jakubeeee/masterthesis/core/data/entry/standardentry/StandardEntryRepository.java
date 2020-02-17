package com.jakubeeee.masterthesis.core.data.entry.standardentry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StandardEntryRepository extends CrudRepository<StandardEntry, Long> {

}
