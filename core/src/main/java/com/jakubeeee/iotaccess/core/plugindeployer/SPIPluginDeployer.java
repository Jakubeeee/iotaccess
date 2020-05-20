package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.iotaccess.core.webservice.FetchPluginRestClient;
import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ServiceLoader;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.StreamSupport.stream;

@Component
class SPIPluginDeployer extends BasePluginDeployer {

    private static final String IDENTIFIER = "spi_plugin_deployer";

    @Value("${deployer.spi.interval:-1}")
    private long spiDeployerInterval;

    public SPIPluginDeployer(
            PluginMetadataService pluginMetadataService,
            ProcessMetadataService processMetadataService,
            FetchPluginRestClient restClient,
            DataPersistStrategyFactory dataPersistStrategyFactory,
            TaskScheduleService taskScheduleService) {
        super(pluginMetadataService, processMetadataService, restClient, dataPersistStrategyFactory,
                taskScheduleService);
    }

    @Override
    public Set<PluginConnector> scanForPlugins() {
        ServiceLoader<PluginConnector> loader = ServiceLoader.load(PluginConnector.class);
        return stream(loader.spliterator(), true)
                .collect(toSet());
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public RegistrationStrategy getAssociatedStrategy() {
        return RegistrationStrategy.SPI;
    }

    @Override
    public long getInterval() {
        return spiDeployerInterval;
    }

}
