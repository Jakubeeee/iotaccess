package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.core.jobschedule.JobScheduleService;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.masterthesis.core.webservice.FetchPluginRestClient;
import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Collections.emptySet;

@Slf4j
@Component
class FilesystemPluginDeployer extends BasePluginDeployer {

    private static final String IDENTIFICATION = "Filesystem Plugin Deployer";

    @Value("${deployer.fs.interval:-1}")
    private long fsDeployerInterval;

    @Value("${deployer.fs.delay:-1}")
    private long fsDeployerDelay;

    public FilesystemPluginDeployer(
            PluginMetadataService pluginMetadataService,
            ProcessMetadataService processMetadataService,
            FetchPluginRestClient restClient,
            DataPersistStrategyFactory dataPersistStrategyFactory,
            JobScheduleService jobScheduleService) {
        super(pluginMetadataService, processMetadataService, restClient, dataPersistStrategyFactory,
                jobScheduleService);
    }

    @Override
    Set<PluginConnector> scanForPlugins() {
        // TODO implement this deployer
        LOG.warn("The {} is not implemented yet. It will not be able to find any plugins", getIdentifier());
        return emptySet();
    }

    @Override
    public String getIdentifier() {
        return IDENTIFICATION;
    }

    @Override
    public RegistrationStrategy getAssociatedStrategy() {
        return RegistrationStrategy.FILESYSTEM;
    }

    @Override
    public long getInterval() {
        return fsDeployerInterval;
    }

    @Override
    public long getInitialDelay() {
        return fsDeployerDelay;
    }

}
