package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.jobschedule.ScheduledJob;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategy;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.masterthesis.core.webservice.FetchPluginRestClient;
import com.jakubeeee.masterthesis.pluginapi.config.FetchConfig;
import com.jakubeeee.masterthesis.pluginapi.config.ProcessConfig;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;
import com.jakubeeee.masterthesis.pluginapi.converter.DataFormat;
import com.jakubeeee.masterthesis.pluginapi.converter.DataType;
import com.jakubeeee.masterthesis.pluginapi.property.FetchedContainer;
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
        LOG.debug("Starting the execution of process job: \"{}\"", identifier);
        String rawFetchedData = fetchData(fetchConfig);
        FetchedContainer convertedData = convertData(rawFetchedData, dataFormat);
        persistData(convertedData, dataType, identifier);
    }

    private String fetchData(FetchConfig config) {
        LOG.trace("Starting to fetch raw data");
        String rawData = restClient.fetchData(config);
        LOG.trace("Raw data fetched successfully");
        return rawData;
    }

    private FetchedContainer convertData(String rawData, DataFormat dataFormat) {
        return converter.convert(rawData, dataFormat);
    }

    private void persistData(FetchedContainer container, DataType dataType, String processIdentifier) {
        DataPersistStrategy persistStrategy = persistStrategyFactory.getStrategy(dataType);
        persistStrategy.persist(container, processIdentifier);
    }

}
