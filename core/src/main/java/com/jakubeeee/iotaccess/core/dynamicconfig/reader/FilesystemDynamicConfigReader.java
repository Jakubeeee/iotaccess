package com.jakubeeee.iotaccess.core.dynamicconfig.reader;

import com.jakubeeee.iotaccess.core.dynamicconfig.DynamicConfigContainer;
import com.jakubeeee.iotaccess.core.dynamicconfig.DynamicConfigEntry;
import com.jakubeeee.iotaccess.core.misc.SortedProperties;
import com.jakubeeee.iotaccess.core.taskschedule.TaskScheduleService;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static java.nio.file.Files.*;
import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;

@Slf4j
@Component
class FilesystemDynamicConfigReader extends BaseDynamicConfigReader {

    private static final String IDENTIFIER = "filesystem_dynamic_config_reader";

    @Value("${dynamic.config.reader.filesystem.interval:-1}")
    private long filesystemReaderInterval;

    @Value("${dynamic.config.reader.filesystem.location}")
    private String filesystemReaderLocation;

    public FilesystemDynamicConfigReader(TaskScheduleService scheduleService) {
        super(scheduleService);
    }

    @Override
    DynamicConfigContainer scanForConfiguration() {
        validatePreconditions();
        Optional<Path> propertiesPathO = resolvePathToPropertiesFile();
        if (propertiesPathO.isPresent()) {
            Properties resolvedProperties = loadProperties(propertiesPathO.get());
            return constructContainer(resolvedProperties);
        } else
            LOG.trace("No configuration found by {}", getIdentifier());
        return constructEmptyContainer();
    }

    private void validatePreconditions() {
        validateImportLocationProperlySet();
        validateImportLocationPresence();
    }

    private void validateImportLocationProperlySet() {
        if (filesystemReaderLocation == null)
            throw new IllegalStateException(
                    format("No mandatory host location has been given for the \"{0}\" to pull the configuration from",
                            getIdentifier()));
    }

    private void validateImportLocationPresence() {
        var importLocationPath = Path.of(filesystemReaderLocation);
        if (notExists(importLocationPath) || !isDirectory(importLocationPath))
            throw new IllegalStateException(
                    format("The host directory \"{0}\" to pull the configuration from by \"{1}\" could not be found",
                            filesystemReaderLocation, getIdentifier()));
    }

    @Synchronized
    private Optional<Path> resolvePathToPropertiesFile() {
        var importLocationPath = Path.of(filesystemReaderLocation);
        try (Stream<Path> allPathsStream = walk(importLocationPath, 1)) {
            List<Path> foundPropertyFilesPaths = allPathsStream
                    .filter(path -> path.toString().endsWith(".properties"))
                    .toList();
            if (foundPropertyFilesPaths.isEmpty())
                return Optional.empty();
            else if (foundPropertyFilesPaths.size() > 1)
                throw new IllegalStateException(
                        format("Multiple configuration properties files found in path \"{0}\" by \"{1}\"",
                                importLocationPath, getIdentifier()));
            else
                return Optional.of(foundPropertyFilesPaths.get(0));
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during searching for configuration files using \"{0}\". Details in underlying exception message",
                            getIdentifier()), e);
        }
    }

    Properties loadProperties(Path propertiesFilePath) {
        try (var propertyFileInputStream = newInputStream(propertiesFilePath)) {
            var properties = new SortedProperties();
            properties.load(propertyFileInputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during loading property file \"{0}\" using \"{1}\". " +
                            "Details in underlying exception message", propertiesFilePath, getIdentifier()), e);
        }
    }

    DynamicConfigContainer constructContainer(Properties properties) {
        List<DynamicConfigEntry> entries = properties.stringPropertyNames().stream()
                .map(propertyName -> new DynamicConfigEntry(propertyName, properties.getProperty(propertyName)))
                .toList();
        return new DynamicConfigContainer(entries);
    }

    DynamicConfigContainer constructEmptyContainer() {
        return new DynamicConfigContainer(emptyList());
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public long getInterval() {
        return filesystemReaderInterval;
    }

}
