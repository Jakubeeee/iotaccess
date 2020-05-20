package com.jakubeeee.iotaccess.core.dynamicconfig.reader;

import com.jakubeeee.iotaccess.core.data.metadata.InitialMetadataSweeper;
import com.jakubeeee.iotaccess.core.data.metadata.dynamicconfigreader.DynamicConfigReaderMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.dynamicconfigreader.DynamicConfigReaderMetadataService;
import com.jakubeeee.iotaccess.core.taskschedule.ScheduledTaskId;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import com.jakubeeee.iotaccess.core.taskschedule.UnmodifiableTaskContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Order(InitialMetadataSweeper.INITIAL_METADATA_SWEEPER_ORDER + 1)
@Component
public class DynamicConfigReaderRegistry implements ApplicationRunner {

    private static final String GROUP_IDENTIFIER = "dynamic_config_readers_group";

    private final DynamicConfigReaderMetadataService dynamicConfigReaderMetadataService;

    private final TaskScheduleService taskScheduleService;

    private final Set<DynamicConfigReader> configurationReaders;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        saveMetadataSet(configurationReaders);
        registerReaders(configurationReaders);
    }

    private void saveMetadataSet(Set<DynamicConfigReader> configReaders) {
        for (var configReader : configReaders)
            saveMetadata(configReader);
    }

    private void saveMetadata(DynamicConfigReader configReader) {
        var configReaderMetadata = new DynamicConfigReaderMetadata(
                configReader.getIdentifier(),
                configReader.getInterval());
        dynamicConfigReaderMetadataService.save(configReaderMetadata);
    }

    private void registerReaders(Set<DynamicConfigReader> configReaders) {
        for (var configReader : configReaders) {
            var taskIdentifier = new ScheduledTaskId(configReader.getIdentifier(), GROUP_IDENTIFIER);
            var taskContext = new UnmodifiableTaskContext(
                    taskIdentifier,
                    configReader::read,
                    configReader.getInterval());
            taskScheduleService.schedule(taskContext);
            dynamicConfigReaderMetadataService.setRegisteredTrue(configReader.getIdentifier());
            LOG.trace("Registered dynamic configuration reader with identifier: \"{}\"", configReader.getIdentifier());
        }
    }

}
