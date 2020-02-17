package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.core.jobschedule.JobScheduleService;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.masterthesis.core.webservice.FetchPluginRestClient;
import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ServiceLoader;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

@Component
class SPIPluginDeployer extends BasePluginDeployer {

    private static final String IDENTIFICATION = "SPI Plugin Deployer";

    @Value("${deployer.spi.interval:-1}")
    private long spiDeployerInterval;

    @Value("${deployer.spi.delay:-1}")
    private long spiDeployerDelay;

    public SPIPluginDeployer(
            PluginMetadataService pluginMetadataService,
            ProcessMetadataService processMetadataService,
            FetchPluginRestClient restClient,
            DataPersistStrategyFactory dataPersistStrategyFactory,
            JobScheduleService jobScheduleService) {
        super(pluginMetadataService, processMetadataService, restClient, dataPersistStrategyFactory,
                jobScheduleService);
    }

    @Override
    public Set<PluginConnector> scanForPlugins() {
        ServiceLoader<PluginConnector> loader = ServiceLoader.load(PluginConnector.class);
        return stream(loader.spliterator(), true)
                .collect(toSet());
    }

    @Override
    public String getIdentifier() {
        return IDENTIFICATION;
    }

    @Override
    public RegistrationStrategy getAssociatedStrategy() {
        return RegistrationStrategy.SPI;
    }

    @Override
    public long getInterval() {
        return spiDeployerInterval;
    }

    @Override
    public long getInitialDelay() {
        return spiDeployerDelay;
    }

}
