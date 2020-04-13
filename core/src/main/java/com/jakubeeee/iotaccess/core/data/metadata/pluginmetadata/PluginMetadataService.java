package com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata;

import com.jakubeeee.iotaccess.core.data.metadata.MetadataService;
import com.jakubeeee.iotaccess.core.data.MandatoryEntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PluginMetadataService implements MetadataService<PluginMetadata> {

    private final PluginMetadataRepository pluginMetadataRepository;

    public void save(@NonNull PluginMetadata pluginMetadata) {
        pluginMetadataRepository.save(pluginMetadata);
        LOG.trace("New \"{}\" saved: \"{}\"", pluginMetadata.getClass().getSimpleName(), pluginMetadata);
    }

    public PluginMetadata findByIdentifier(@NonNull String identifier) {
        return pluginMetadataRepository.findByIdentifier(identifier).orElseThrow(
                () -> new MandatoryEntityNotFoundException(PluginMetadata.class, "identifier", identifier));
    }

    public Optional<PluginMetadata> findOptionalByIdentifier(@NonNull String identifier) {
        return pluginMetadataRepository.findByIdentifier(identifier);
    }

    public boolean isIdentifierUnique(@NonNull String identifier) {
        return pluginMetadataRepository.findByIdentifier(identifier).isEmpty();
    }

    public void setDeployedTrue(@NonNull String identifier) {
        pluginMetadataRepository.updateDeployed(true, identifier);
    }

}