package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.core.jobschedule.JobScheduleService;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.masterthesis.core.webservice.FetchPluginRestClient;
import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Collections.emptySet;

@Slf4j
@Component
class DatabasePluginDeployer extends BasePluginDeployer {

    private static final String IDENTIFICATION = "Database Plugin Deployer";

    @Value("${deployer.db.interval:-1}")
    private long dbDeployerInterval;

    @Value("${deployer.db.delay:-1}")
    private long dbDeployerDelay;

    public DatabasePluginDeployer(
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
        return RegistrationStrategy.DATABASE;
    }

    @Override
    public long getInterval() {
        return dbDeployerInterval;
    }

    @Override
    public long getInitialDelay() {
        return dbDeployerDelay;
    }

}
