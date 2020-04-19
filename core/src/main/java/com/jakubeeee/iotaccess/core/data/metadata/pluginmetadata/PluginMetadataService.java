package com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata;

import com.jakubeeee.iotaccess.core.data.MandatoryEntityNotFoundException;
import com.jakubeeee.iotaccess.core.data.metadata.MetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PluginMetadataService implements MetadataService<PluginMetadata> {

    private final PluginMetadataRepository pluginMetadataRepository;

    private final ProcessMetadataService processMetadataService;

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

    @Transactional
    @Override
    public void delete(PluginMetadata pluginMetadata) {
        processMetadataService.deleteAllByParent(pluginMetadata);
        pluginMetadataRepository.delete(pluginMetadata);
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (var pluginMetadata : pluginMetadataRepository.findAll())
            delete(pluginMetadata);
    }

}
