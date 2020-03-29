package com.jakubeeee.masterthesis.core.plugindeployer;

import com.jakubeeee.masterthesis.pluginapi.PluginConnector;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableSet;

class DynamicPluginClassLoader extends ClassLoader {

    public DynamicPluginClassLoader(ClassLoader parent) {
        super(parent);
    }

    public PluginConnector loadPluginFromJarFile(JarFile jarFile) {
        Set<LoadCandidate> candidates = createLoadCandidates(jarFile);
        Set<Class<?>> definedClasses = defineClasses(candidates);
        Class<?> pluginConnectorClass = findPluginConnectorClass(definedClasses);
        return createPluginConnectorInstance(pluginConnectorClass);
    }

    private Set<LoadCandidate> createLoadCandidates(JarFile jarFile) {
        Set<JarEntry> classEntries = extractClassEntries(jarFile);
        var loadCandidates = new HashSet<LoadCandidate>();
        for (var classEntry : classEntries) {
            LoadCandidate candidate = createLoadCandidate(jarFile, classEntry);
            loadCandidates.add(candidate);
        }
        return loadCandidates;
    }

    private Set<JarEntry> extractClassEntries(JarFile jarFile) {
        return jarFile.stream()
                .filter(jarEntry -> jarEntry.getName().endsWith(".class"))
                .collect(toUnmodifiableSet());
    }

    private LoadCandidate createLoadCandidate(JarFile jarFile, JarEntry classEntry) {
        String fullClassName = transformToFullClassName(classEntry.getName());
        verifyClassNotAlreadyLoaded(fullClassName);
        InputStream classInputStream;
        try {
            classInputStream = jarFile.getInputStream(classEntry);
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during opening jar \"{0}\" input stream for entry \"{1}\". " +
                            "Details in underlying exception message", jarFile.getName(), classEntry.getName()), e);
        }
        return new LoadCandidate(fullClassName, classInputStream);
    }

    private String transformToFullClassName(String rawClassPath) {
        int borderIndex = rawClassPath.lastIndexOf('/') + 1;
        String packageName = rawClassPath.substring(0, borderIndex).replace('/', '.');
        String className = rawClassPath.substring(borderIndex, rawClassPath.indexOf('.'));
        return packageName + className;
    }

    private void verifyClassNotAlreadyLoaded(String className) {
        verifyClassNotAlreadyLoadedBySystemClassLoader(className);
        verifyClassNotAlreadyLoadedByThisClassLoader(className);
    }

    private void verifyClassNotAlreadyLoadedBySystemClassLoader(String className) {
        try {
            findSystemClass(className);
        } catch (ClassNotFoundException e) {
            // using the exception handling for control flow is not the best practice, but the ClassLoader API does not allow to perform this check in any other way
            return;
        }
        throw new PluginClassAlreadyLoadedException(
                "Class \"{0}\" was already loaded by system class loader during application startup", className);
    }

    private void verifyClassNotAlreadyLoadedByThisClassLoader(String className) {
        Class<?> loadedClass = findLoadedClass(className);
        if (loadedClass != null)
            throw new PluginClassAlreadyLoadedException(
                    "Class \"{0}\" was already dynamically loaded by \"{1}\"", className,
                    DynamicPluginClassLoader.class.getSimpleName());
    }

    private Set<Class<?>> defineClasses(Set<LoadCandidate> candidates) {
        var definedClasses = new HashSet<Class<?>>();
        for (var candidate : candidates) {
            Class<?> loadedClass = defineClass(candidate);
            definedClasses.add(loadedClass);
        }
        return definedClasses;
    }

    private Class<?> defineClass(LoadCandidate candidate) {
        byte[] classData = tryReadBinaryClassData(candidate);
        return defineClass(candidate.className(), classData, 0, classData.length);
    }

    private byte[] tryReadBinaryClassData(LoadCandidate candidate) {
        try (candidate) {
            return readBinaryClassData(candidate);
        } catch (IOException e) {
            throw new IllegalStateException(
                    format("An exception has occurred during loading a class from jar file using \"{0}\". " +
                            "Details in underlying exception message", this.getClass().getSimpleName()), e);
        }
    }

    private byte[] readBinaryClassData(LoadCandidate candidate) throws IOException {
        var classInputStream = candidate.inputStream();
        var buffer = new ByteArrayOutputStream();
        int data = classInputStream.read();
        while (data != -1) {
            buffer.write(data);
            data = classInputStream.read();
        }
        return buffer.toByteArray();
    }

    private Class<?> findPluginConnectorClass(Set<Class<?>> definedClasses) {
        Set<Class<?>> pluginConnectors = definedClasses.stream()
                .filter(this::isPluginConnector)
                .collect(toUnmodifiableSet());
        validateExactlyOnePluginConnectorFound(pluginConnectors);
        return pluginConnectors.iterator().next();
    }

    private boolean isPluginConnector(Class<?> definedClass) {
        return stream(definedClass.getInterfaces())
                .collect(toSet())
                .contains(PluginConnector.class);
    }

    private void validateExactlyOnePluginConnectorFound(Set<Class<?>> pluginConnectors) {
        if (pluginConnectors.isEmpty())
            throw new IllegalStateException(format("No mandatory \"{0}\" implementation found in processed jar file",
                    PluginConnector.class.getSimpleName()));
        else if (pluginConnectors.size() > 1)
            throw new IllegalStateException(format("Multiple \"{0}\" implementations found in processed jar file",
                    PluginConnector.class.getSimpleName()));
    }

    private PluginConnector createPluginConnectorInstance(Class<?> pluginConnectorClass) {
        try {
            return (PluginConnector) pluginConnectorClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(
                    format("Could not create a new instance of plugin connector implementation: \"{0}\". " +
                            "Details in underlying exception message", pluginConnectorClass.getSimpleName()), e);
        }
    }

    private static record LoadCandidate(String className, InputStream inputStream) implements Closeable {
        @Override public void close() throws IOException {
            inputStream.close();
        }
    }

    private static class PluginClassAlreadyLoadedException extends RuntimeException {
        public PluginClassAlreadyLoadedException(String message, Object... parameters) {
            super(format(message, parameters));
        }
    }

}

