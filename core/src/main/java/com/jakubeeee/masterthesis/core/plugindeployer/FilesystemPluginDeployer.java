package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.core.data.metadata.pluginmetadata.PluginMetadataService;
import com.jakubeeee.masterthesis.core.data.metadata.processmetadata.ProcessMetadataService;
import com.jakubeeee.masterthesis.core.jobschedule.JobScheduleService;
import com.jakubeeee.masterthesis.core.persistence.DataPersistStrategyFactory;
import com.jakubeeee.masterthesis.core.webservice.FetchPluginRestClient;
import com.jakubeeee.masterthesis.pluginapi.PluginConnector;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Stream;

import static java.nio.file.Files.*;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Slf4j
@Component
class FilesystemPluginDeployer extends BasePluginDeployer {

    private static final String IDENTIFIER = "Filesystem Plugin Deployer";

    private static final String ARCHIVE_SUBDIRECTORY = "/archive/";

    private final DynamicPluginClassLoader classLoader;

    @Value("${deployer.fs.interval:-1}")
    private long fsDeployerInterval;

    @Value("${deployer.fs.delay:-1}")
    private long fsDeployerDelay;

    @Value("${deployer.fs.location}")
    private String fsDeployerLocation;

    public FilesystemPluginDeployer(
            PluginMetadataService pluginMetadataService,
            ProcessMetadataService processMetadataService,
            FetchPluginRestClient restClient,
            DataPersistStrategyFactory dataPersistStrategyFactory,
            JobScheduleService jobScheduleService) {
        super(pluginMetadataService, processMetadataService, restClient, dataPersistStrategyFactory,
                jobScheduleService);
        classLoader = new DynamicPluginClassLoader(this.getClass().getClassLoader());
    }

    @Override
    Set<PluginConnector> scanForPlugins() {
        validatePreconditions();
        createArchiveDirectoryIfNotPresent();
        Set<Path> jarPaths = resolvePathsToJarArchives();
        return processPluginJars(jarPaths);
    }

    private void validatePreconditions() {
        validateImportLocationProperlySet();
        validateImportLocationPresence();
    }

    private void validateImportLocationProperlySet() {
        if (fsDeployerLocation == null)
            throw new IllegalStateException(
                    format("No mandatory host location has been given for the \"{0}\" to pull the plugin from",
                            getIdentifier()));
    }

    private void validateImportLocationPresence() {
        var importLocationPath = Path.of(fsDeployerLocation);
        if (notExists(importLocationPath) || !isDirectory(importLocationPath))
            throw new IllegalStateException(
                    format("The host directory \"{0}\" to pull the plugins from by \"{1}\" could not be found",
                            fsDeployerLocation, getIdentifier()));
    }

    @Synchronized
    private void createArchiveDirectoryIfNotPresent() {
        var archivePath = Path.of(fsDeployerLocation + ARCHIVE_SUBDIRECTORY);
        try {
            if (notExists(archivePath))
                createDirectory(archivePath);
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("The archive directory \"{0}\" could not be created. Details in underlying exception message",
                            archivePath), e);
        }
    }

    @Synchronized
    private Set<Path> resolvePathsToJarArchives() {
        var importLocationPath = Path.of(fsDeployerLocation);
        try (Stream<Path> allPathsStream = walk(importLocationPath, 1)) {
            return allPathsStream
                    .filter(path -> path.toString().endsWith(".jar"))
                    .collect(toUnmodifiableSet());
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during searching for plugins using \"{0}\". Details in underlying exception message",
                            getIdentifier()), e);
        }
    }

    private Set<PluginConnector> processPluginJars(Set<Path> jarPaths) {
        var connectors = new HashSet<PluginConnector>();
        for (var jarPath : jarPaths) {
            PluginConnector connector = processPluginJar(jarPath);
            connectors.add(connector);
        }
        return connectors;
    }

    private PluginConnector processPluginJar(Path jarPath) {
        try (var jarFile = new JarFile(jarPath.toFile())) {
            return classLoader.loadPluginFromJarFile(jarFile);
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during loading classes from jar file \"{0}\" using \"{1}\". " +
                            "Details in underlying exception message", jarPath, getIdentifier()), e);
        } finally {
            moveJarFileToArchiveDirectory(jarPath);
        }
    }

    private void moveJarFileToArchiveDirectory(Path jarPath) {
        var rawArchiveDirectoryPath = fsDeployerLocation + ARCHIVE_SUBDIRECTORY;
        var moveDateTime = Instant.now();
        var rawDateStamp = String.valueOf(moveDateTime.toEpochMilli());
        var archivedJarName = jarPath.getFileName() + ".imported_" + rawDateStamp;
        var archivedJarPath = Path.of(rawArchiveDirectoryPath + archivedJarName);
        try {
            move(jarPath, archivedJarPath);
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during moving of processed jar file to archive directory \"{0}\" using \"{1}\". " +
                            "Details in underlying exception message", rawArchiveDirectoryPath, getIdentifier()), e);
        }
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public RegistrationStrategy getAssociatedStrategy() {
        return RegistrationStrategy.FILESYSTEM;
    }

    @Override
    public long getInterval() {
        return fsDeployerInterval;
    }

    @Override
    public long getInitialDelay() {
        return fsDeployerDelay;
    }

}
