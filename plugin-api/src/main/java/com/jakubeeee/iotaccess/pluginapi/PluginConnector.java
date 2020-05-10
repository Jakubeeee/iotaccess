package com.jakubeeee.iotaccess.pluginapi;

import com.jakubeeee.iotaccess.pluginapi.config.PluginConfig;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;

import java.util.Set;

/**
 * Entry point of a plugin module that specifies its core elements and makes it compatible with the main engine. It is
 * responsible for providing the plugin's configuration in form of {@link PluginConfig} and raw data into common object
 * format conversion mechanisms in form of {@link DataConverter} objects.
 */
public interface PluginConnector {

    Set<DataConverter> getConverters();

    PluginConfig getConfig();

}
