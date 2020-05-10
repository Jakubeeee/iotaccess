package com.jakubeeee.iotaccess.core.data.metadata.deployermetadata;

import com.jakubeeee.iotaccess.core.data.MandatoryEntityNotFoundException;
import com.jakubeeee.iotaccess.core.data.metadata.MetadataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeployerMetadataService implements MetadataService<DeployerMetadata> {

    private final DeployerMetadataRepository deployerMetadataRepository;

    public void save(@NonNull DeployerMetadata deployerMetadata) {
        deployerMetadataRepository.save(deployerMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", deployerMetadata.getClass().getSimpleName(), deployerMetadata);
    }

    @Override
    public DeployerMetadata findByIdentifier(@NonNull String identifier) {
        return deployerMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(DeployerMetadata.class, "identifier", identifier));
    }

    @Override
    public Optional<DeployerMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return deployerMetadataRepository.findByIdentifier(identifier);
    }

    public boolean isIdentifierUnique(@NonNull String identifier) {
        return deployerMetadataRepository.findByIdentifier(identifier).isEmpty();
    }

    public void setRegisteredTrue(@NonNull String identifier) {
        deployerMetadataRepository.updateRegistered(true, identifier);
    }

    @Transactional
    @Override
    public void delete(@NonNull DeployerMetadata deployerMetadata) {
        deployerMetadataRepository.delete(deployerMetadata);
    }

    @Transactional
    @Override
    public void deleteAll() {
        deployerMetadataRepository.deleteAll();
    }

}
