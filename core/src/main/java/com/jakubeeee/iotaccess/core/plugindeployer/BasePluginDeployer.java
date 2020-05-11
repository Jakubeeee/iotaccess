package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.iotaccess.core.taskschedule.ScheduledTaskConfig;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import com.jakubeeee.iotaccess.core.webservice.FetchPluginRestClient;
import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import com.jakubeeee.iotaccess.pluginapi.config.ConverterConfig;
import com.jakubeeee.iotaccess.pluginapi.config.PluginConfig;
import com.jakubeeee.iotaccess.pluginapi.config.ProcessConfig;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
abstract class BasePluginDeployer implements PluginDeployer {

    private static final String PLUGIN_PROCESSES_TASK_GROUP_NAME = "plugin_processes_group";

    private final PluginMetadataService pluginMetadataService;

    private final ProcessMetadataService processMetadataService;

    private final FetchPluginRestClient restClient;

    private final DataPersistStrategyFactory dataPersistStrategyFactory;

    private final TaskScheduleService taskScheduleService;

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
        var processMetadata = new ProcessMetadata(processConfig.getIdentifier(),
                processConfig.getDescription(),
                processConfig.getFetchConfig().getUrl(),
                processConfig.getConverterConfig().getConverterIdentifier(),
                processConfig.getScheduleConfig().getInterval(),
                parentPluginMetadata);
        processMetadataService.save(processMetadata);
    }

    private void deployPlugin(PluginConnector connector) {
        PluginConfig pluginConfig = connector.getConfig();
        Set<ProcessConfig> processConfigs = pluginConfig.getProcessConfigs();
        Set<DataConverter> dataConverters = connector.getConverters();
        deployProcesses(processConfigs, dataConverters);
        pluginMetadataService.setDeployedTrue(pluginConfig.getIdentifier());
        LOG.info("Successfully deployed plugin with identifier \"{}\"", pluginConfig.getIdentifier());
    }

    private void deployProcesses(Set<ProcessConfig> processConfigs, Set<DataConverter> dataConverters) {
        for (var processConfig : processConfigs) {
            ConverterConfig converterConfig = processConfig.getConverterConfig();
            String converterIdentifier = converterConfig.getConverterIdentifier();
            DataConverter dataConverter = getMatchingConverter(converterIdentifier, dataConverters);
            deployProcess(processConfig, dataConverter);
        }
    }

    private DataConverter getMatchingConverter(String identifier, Set<DataConverter> dataConverters) {
        List<DataConverter> applicableConverters = dataConverters.stream()
                .filter(converter -> converter.getIdentifier().equalsIgnoreCase(identifier.trim()))
                .collect(toList());
        ensureExactlyOneConverterMatched(identifier, applicableConverters);
        return applicableConverters.get(0);
    }

    private void ensureExactlyOneConverterMatched(String identifier, List<DataConverter> applicableConverters) {
        if (applicableConverters.isEmpty())
            throw new InvalidConverterConfigException(
                    "There is no matching converter with identifier \"{0}\" in given collection of converters: \"{1}\"",
                    identifier, applicableConverters);
        else if (applicableConverters.size() > 1)
            throw new InvalidConverterConfigException(
                    "There are multiple converters with identifier \"{0}\" in given collection of converters: \"{1}\"",
                    identifier, applicableConverters);
    }

    private void deployProcess(ProcessConfig processConfig, DataConverter converter) {
        var job = new ProcessScheduledTask(processConfig, converter, restClient, dataPersistStrategyFactory);
        long interval = processConfig.getScheduleConfig().getInterval();
        var scheduledTaskConfig = new ScheduledTaskConfig(
                processConfig.getIdentifier().replaceAll("\\s", ""), PLUGIN_PROCESSES_TASK_GROUP_NAME, interval);
        taskScheduleService.schedule(job, scheduledTaskConfig);
        LOG.debug("Successfully deployed process with identifier \"{}\"", processConfig.getIdentifier());
    }

}
