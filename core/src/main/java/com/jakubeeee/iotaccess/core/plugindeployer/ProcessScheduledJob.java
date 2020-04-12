package com.jakubeeee.iotaccess.core.plugindeployer;

import com.jakubeeee.iotaccess.core.jobschedule.ScheduledJob;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategy;
import com.jakubeeee.iotaccess.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.iotaccess.core.webservice.FetchPluginRestClient;
import com.jakubeeee.iotaccess.pluginapi.config.FetchConfig;
import com.jakubeeee.iotaccess.pluginapi.config.ProcessConfig;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;
import com.jakubeeee.iotaccess.pluginapi.converter.DataFormat;
import com.jakubeeee.iotaccess.pluginapi.converter.DataType;
import com.jakubeeee.iotaccess.pluginapi.property.FetchedContainer;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Value
final class ProcessScheduledJob implements ScheduledJob {

    private final ProcessConfig config;

    private final DataConverter converter;

    private final FetchPluginRestClient restClient;

    private final DataPersistStrategyFactory persistStrategyFactory;

    @Override
    public void run() {
        String identifier = config.getIdentifier();
        FetchConfig fetchConfig = config.getFetchConfig();
        DataFormat dataFormat = fetchConfig.getDataFormat();
        DataType dataType = fetchConfig.getDataType();
        performAllProcessActions(identifier, fetchConfig, dataFormat, dataType);
    }

    private void performAllProcessActions(String identifier,
                                          FetchConfig fetchConfig,
                                          DataFormat dataFormat,
                                          DataType dataType) {
        LOG.debug("Starting the execution of process with identifier: \"{}\"", identifier);
        String rawFetchedData = fetchData(fetchConfig);
        FetchedContainer convertedData = convertData(rawFetchedData, dataFormat);
        persistData(convertedData, dataType, identifier);
        LOG.debug("Successfully ended the execution of process with identifier: \"{}\"", identifier);
    }

    private String fetchData(FetchConfig config) {
        LOG.trace("Starting to fetch raw data of format \"{}\" from \"{}\"", config.getDataFormat(), config.getUrl());
        String rawData = restClient.fetchData(config);
        LOG.trace("Successfully fetched raw data: \"{}\"", rawData);
        return rawData;
    }

    private FetchedContainer convertData(String rawData, DataFormat dataFormat) {
        LOG.trace("Starting the conversion of data from raw form to object form using \"{}\"",
                converter.getClass().getSimpleName());
        FetchedContainer container = converter.convert(rawData, dataFormat);
        LOG.trace("Successfully converted data from raw form to object form using \"{}\" with result \"{}\"",
                converter.getClass().getSimpleName(), container);
        return container;
    }

    private void persistData(FetchedContainer container, DataType dataType, String processIdentifier) {
        DataPersistStrategy persistStrategy = persistStrategyFactory.getStrategy(dataType);
        LOG.trace("Starting to persist data using \"{}\"", persistStrategy);
        persistStrategy.persist(container, processIdentifier);
        LOG.trace("Successfully persisted data using \"{}\"", persistStrategy);
    }

}
