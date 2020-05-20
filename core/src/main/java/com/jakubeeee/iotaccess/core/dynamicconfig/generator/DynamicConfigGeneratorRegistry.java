package com.jakubeeee.iotaccess.core.dynamicconfig.generator;

import com.jakubeeee.iotaccess.core.data.metadata.InitialMetadataSweeper;
import com.jakubeeee.iotaccess.core.data.metadata.dynamicconfiggenerator.DynamicConfigGeneratorMetadata;
import com.jakubeeee.iotaccess.core.data.metadata.dynamicconfiggenerator.DynamicConfigGeneratorMetadataService;
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
public class DynamicConfigGeneratorRegistry implements ApplicationRunner {

    private static final String GROUP_IDENTIFIER = "dynamic_config_generators_group";

    private final DynamicConfigGeneratorMetadataService dynamicConfigGeneratorMetadataService;

    private final TaskScheduleService taskScheduleService;

    private final Set<DynamicConfigGenerator> configurationReaders;

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        saveMetadataSet(configurationReaders);
        registerReaders(configurationReaders);
    }

    private void saveMetadataSet(Set<DynamicConfigGenerator> configGenerators) {
        for (var configGenerator : configGenerators)
            saveMetadata(configGenerator);
    }

    private void saveMetadata(DynamicConfigGenerator configGenerator) {
        var configGeneratorMetadata = new DynamicConfigGeneratorMetadata(
                configGenerator.getIdentifier(),
                configGenerator.getInterval());
        dynamicConfigGeneratorMetadataService.save(configGeneratorMetadata);
    }

    private void registerReaders(Set<DynamicConfigGenerator> configGenerators) {
        for (var configGenerator : configGenerators) {
            var taskIdentifier = new ScheduledTaskId(configGenerator.getIdentifier(), GROUP_IDENTIFIER);
            var taskContext = new UnmodifiableTaskContext(
                    taskIdentifier,
                    configGenerator::generate,
                    configGenerator.getInterval());
            taskScheduleService.schedule(taskContext);
            dynamicConfigGeneratorMetadataService.setRegisteredTrue(configGenerator.getIdentifier());
            LOG.trace("Registered dynamic configuration generator with identifier: \"{}\"",
                    configGenerator.getIdentifier());
        }
    }

}
