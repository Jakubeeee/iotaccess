package com.jakubeeee.masterthesis.core.data.metadata.processmetadata;

import com.jakubeeee.masterthesis.core.data.metadata.MetadataService;
import com.jakubeeee.masterthesis.core.data.MandatoryEntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProcessMetadataService implements MetadataService<ProcessMetadata> {

    private final ProcessMetadataRepository processMetadataRepository;

    public void save(@NonNull ProcessMetadata processMetadata) {
        processMetadataRepository.save(processMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", processMetadata.getClass().getSimpleName(), processMetadata);
    }

    public ProcessMetadata findByIdentifier(@NonNull String identifier) {
        return processMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(ProcessMetadata.class, "identifier", identifier));
    }

    public Optional<ProcessMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return processMetadataRepository.findByIdentifier(identifier);
    }

}
