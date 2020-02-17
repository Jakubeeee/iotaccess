package com.jakubeeee.masterthesis.core.data.metadata.processmetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataService;
import com.jakubeeee.masterthesis.core.misc.MandatoryEntityNotFound;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Default service related to operations on {@link ProcessMetadata} objects.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProcessMetadataService implements MetadataService<ProcessMetadata> {

    private final ProcessMetadataRepository processMetadataRepository;

    public void save(@NonNull ProcessMetadata processMetadata) {
        processMetadataRepository.save(processMetadata);
    }

    public ProcessMetadata findByIdentifier(@NonNull String processIdentifier) {
        return processMetadataRepository.findByIdentifier(processIdentifier).orElseThrow(MandatoryEntityNotFound::new);
    }

    public Optional<ProcessMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return processMetadataRepository.findByIdentifier(identifier);
    }

}
