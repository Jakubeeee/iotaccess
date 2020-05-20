package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategy;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.iotaccess.core.taskschedule.*;
import com.jakubeeee.iotaccess.core.webservice.FetchPluginRestClient;
import com.jakubeeee.iotaccess.pluginapi.PluginConnector;
import com.jakubeeee.iotaccess.pluginapi.config.*;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.converter.DataType;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.jakubeeee.iotaccess.core.plugindeployer.ProcessTaskParameterConstants.*;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
abstract class BasePluginDeployer implements PluginDeployer {

    private static final String GROUP_IDENTIFIER = "plugin_processes_group";

    private final PluginMetadataService pluginMetadataService;

    private final ProcessMetadataService processMetadataService;

    private final FetchPluginRestClient restClient;

    private final DataPersistStrategyFactory dataPersistStrategyFactory;

    private final TaskScheduleService taskScheduleService;

    @Override
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
        for (var processConfig : processConfigs)
            deployProcess(processConfig, dataConverters);
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

    private void deployProcess(ProcessConfig processConfig, Set<DataConverter> converters) {
        var taskIdentifier = new ScheduledTaskId(processConfig.getIdentifier(), GROUP_IDENTIFIER);
        String processIdentifier = processConfig.getIdentifier();

        ConverterConfig converterConfig = processConfig.getConverterConfig();
        String converterIdentifier = converterConfig.getConverterIdentifier();

        FetchConfig fetchConfig = processConfig.getFetchConfig();
        String url = fetchConfig.getUrl();
        DataFormat dataFormat = fetchConfig.getDataFormat();
        DataType dataType = fetchConfig.getDataType();

        ScheduleConfig scheduleConfig = processConfig.getScheduleConfig();
        long interval = scheduleConfig.getInterval();

        var taskProperties = new TaskParametersContainer(Set.of(
                new StandardTaskParameter<>(ALL_CONVERTERS_PARAMETER, converters),
                new StandardTaskParameter<>(PROCESS_IDENTIFIER_PARAMETER, processIdentifier),
                new DynamicTaskParameter(FETCH_URL_PARAMETER, url),
                new DynamicTaskParameter(FETCH_DATA_FORMAT_PARAMETER, dataFormat.name()),
                new DynamicTaskParameter(FETCH_DATA_TYPE_PARAMETER, dataType.name()),
                new DynamicTaskParameter(CONVERTER_IDENTIFIER_PARAMETER, converterIdentifier)
        ));
        var taskContext = new ParameterizedTaskContext(
                taskIdentifier,
                this::executePluginTask,
                taskProperties,
                interval);
        taskScheduleService.schedule(taskContext, scheduleConfig.isRunning());
        LOG.debug("Successfully deployed process with identifier \"{}\"", processConfig.getIdentifier());
    }

    private void executePluginTask(TaskParametersContainer properties) {
        String processIdentifier = properties.get(PROCESS_IDENTIFIER_PARAMETER);
        String converterIdentifier = properties.get(CONVERTER_IDENTIFIER_PARAMETER);
        @SuppressWarnings("unchecked") Set<DataConverter> dataConverters =
                properties.get(ALL_CONVERTERS_PARAMETER, Set.class);
        String url = properties.get(FETCH_URL_PARAMETER);
        DataFormat dataFormat = DataFormat.valueOf(properties.get(FETCH_DATA_FORMAT_PARAMETER));
        DataType dataType = DataType.valueOf(properties.get(FETCH_DATA_TYPE_PARAMETER));
        LOG.debug("Starting the execution of process with identifier: \"{}\"", processIdentifier);
        DataConverter converter = getMatchingConverter(converterIdentifier, dataConverters);
        String rawFetchedData = fetchData(url, dataFormat);
        FetchedContainer convertedData = convertData(converter, rawFetchedData, dataFormat);
        persistData(convertedData, dataType, processIdentifier);
        LOG.debug("Successfully ended the execution of process with identifier: \"{}\"", processIdentifier);
    }

    private DataConverter getMatchingConverter(String identifier, Set<DataConverter> dataConverters) {
        List<DataConverter> applicableConverters = dataConverters.stream()
                .filter(converter -> converter.getIdentifier().equalsIgnoreCase(identifier.trim()))
                .collect(toList());
        ensureExactlyOneConverterMatched(identifier, applicableConverters);
        return applicableConverters.get(0);
    }

    private String fetchData(String url, DataFormat dataFormat) {
        LOG.trace("Starting to fetch raw data of format \"{}\" from \"{}\"", dataFormat, url);
        String rawData = restClient.fetchData(url);
        LOG.trace("Successfully fetched raw data: \"{}\"", rawData);
        return rawData;
    }

    private FetchedContainer convertData(DataConverter converter, String rawData, DataFormat dataFormat) {
        LOG.trace("Starting the conversion of data from raw form to object form using \"{}\"",
                converter.getClass().getSimpleName());
        FetchedContainer container = converter.convert(rawData, dataFormat);
        LOG.trace("Successfully converted data from raw form to object form using \"{}\" with result \"{}\"",
                converter.getClass().getSimpleName(), container);
        return container;
    }

    private void persistData(FetchedContainer container, DataType dataType, String processIdentifier) {
        DataPersistStrategy persistStrategy = dataPersistStrategyFactory.getStrategy(dataType);
        LOG.trace("Starting to persist data using \"{}\"", persistStrategy);
        persistStrategy.persist(container, processIdentifier);
        LOG.trace("Successfully persisted data using \"{}\"", persistStrategy);
    }

}
