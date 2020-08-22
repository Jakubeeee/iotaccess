package com.jakubeeee.iotaccess.core.dynamicconfig.generator;

import com.jakubeeee.iotaccess.core.dynamicconfig.DynamicConfigContainer;
import com.jakubeeee.iotaccess.core.misc.SortedProperties;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.jakubeeee.iotaccess.core.data.metadata.InitialMetadataSweeper.INITIAL_METADATA_SWEEPER_ORDER;
import static java.text.MessageFormat.format;

@Slf4j
@Order(INITIAL_METADATA_SWEEPER_ORDER + 1)
@Component
class FilesystemDynamicConfigGenerator extends BaseDynamicConfigGenerator {

    private static final String IDENTIFIER = "filesystem_dynamic_config_generator";

    private static final String GENERATED_FILE_NAME = "/dynamic_config.properties";

    @Value("${dynamic.config.generator.filesystem.interval:-1}")
    private long filesystemGeneratorInterval;

    @Value("${dynamic.config.generator.filesystem.location}")
    private String filesystemGeneratorLocation;

    public FilesystemDynamicConfigGenerator(TaskScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    void generateConfiguration(DynamicConfigContainer configContainer) {
        var properties = new SortedProperties();
        for (var entry : configContainer.entries())
            properties.setProperty(entry.key(), entry.value());
        var outputFilePath = Path.of(filesystemGeneratorLocation + GENERATED_FILE_NAME);
        try (var outputStream = Files.newOutputStream(outputFilePath)) {
            properties.store(outputStream, "File generated by: " + getIdentifier());
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during generation of property file \"{0}\" using \"{1}\". " +
                            "Details in underlying exception message", outputFilePath, getIdentifier()), e);
        }
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long getInterval() {
        return filesystemGeneratorInterval;
    }

}