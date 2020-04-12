package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.core.jobschedule.JobScheduleService;
import com.jakubeeee.iotaccess.core.jobschedule.ScheduledJob;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.iotaccess.core.webservice.FetchPluginRestClient;
import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import com.jakubeeee.iotaccess.pluginapi.config.PluginConfig;
import com.jakubeeee.iotaccess.pluginapi.config.ProcessConfig;
import com.jakubeeee.iotaccess.pluginapi.config.ScheduleConfig;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
abstract class BasePluginDeployer implements PluginDeployer {

    private final PluginMetadataService pluginMetadataService;

    private final ProcessMetadataService processMetadataService;

    private final FetchPluginRestClient restClient;

    private final DataPersistStrategyFactory dataPersistStrategyFactory;

    private final JobScheduleService jobScheduleService;

    @Transactional
    public void deploy() {
        LOG.trace("Invoked execution of \"{}\" with identifier \"{}\"", this.getClass().getSimpleName(),
                getIdentifier());
        Set<PluginConnector> connectors = scanForPlugins();
        connectors.forEach(
                connector -> LOG.trace("Found plugin with identifier \"{}\"", connector.getConfig().getIdentifier()));
        deployPluginsIfApplicable(connectors);
    }

    abstract Set<PluginConnector> scanForPlugins();

    private void deployPluginsIfApplicable(Set<PluginConnector> connectors) {
        for (var connector : connectors)
            deployPluginIfApplicable(connector);
    }

    private void deployPluginIfApplicable(PluginConnector connector) {
        PluginConfig pluginConfig = connector.getConfig();
        String pluginIdentifier = pluginConfig.getIdentifier();
        if (isPluginIdentifierUnique(pluginIdentifier)) {
            savePluginMetadata(pluginConfig);
            deployPlugin(connector);
        } else {
            LOG.trace("Plugin with identifier \"{}\" already deployed. Skipping.", pluginIdentifier);
        }
    }

    private boolean isPluginIdentifierUnique(String pluginIdentifier) {
        return pluginMetadataService.isIdentifierUnique(pluginIdentifier);
    }

    private void savePluginMetadata(PluginConfig pluginConfig) {
        var pluginMetadata = new PluginMetadata(pluginConfig.getIdentifier());
        pluginMetadataService.save(pluginMetadata);
        Set<ProcessConfig> processConfigs = pluginConfig.getProcessConfigs();
        saveProcessesMetadata(processConfigs, pluginMetadata);
    }

    private void saveProcessesMetadata(Set<ProcessConfig> processConfigs, PluginMetadata parentPluginMetadata) {
        for (var processConfig : processConfigs)
            saveProcessMetadata(processConfig, parentPluginMetadata);
    }

    private void saveProcessMetadata(ProcessConfig processConfig, PluginMetadata parentPluginMetadata) {
        var processMetadata = new ProcessMetadata(processConfig.getIdentifier(), processConfig.getDescription(),
                processConfig.getFetchConfig().getUrl(), parentPluginMetadata);
        processMetadataService.save(processMetadata);
    }

    private void deployPlugin(PluginConnector connector) {
        PluginConfig pluginConfig = connector.getConfig();
        Set<ProcessConfig> processConfigs = pluginConfig.getProcessConfigs();
        DataConverter converter = connector.getConverter();
        deployProcesses(processConfigs, converter);
        pluginMetadataService.setDeployedTrue(pluginConfig.getIdentifier());
        LOG.info("Successfully deployed plugin with identifier \"{}\"", pluginConfig.getIdentifier());
    }

    private void deployProcesses(Set<ProcessConfig> processConfigs, DataConverter converter) {
        for (var processConfig : processConfigs)
            deployProcess(processConfig, converter);
    }

    private void deployProcess(ProcessConfig processConfig, DataConverter converter) {
        ScheduleConfig scheduleConfig = processConfig.getScheduleConfig();
        ScheduledJob job = new ProcessScheduledJob(processConfig, converter, restClient, dataPersistStrategyFactory);
        scheduleProcess(job, scheduleConfig);
        LOG.debug("Successfully deployed process with identifier \"{}\"", processConfig.getIdentifier());
    }

    private void scheduleProcess(ScheduledJob job, ScheduleConfig scheduleConfig) {
        long interval = scheduleConfig.getInterval();
        jobScheduleService.schedule(job, interval);
    }

}
