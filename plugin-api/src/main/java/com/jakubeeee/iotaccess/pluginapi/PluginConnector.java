package com.jakubeeee.iotaccess.pluginapi;

import com.jakubeeee.iotaccess.pluginapi.config.PluginConfig;
import com.jakubeeee.iotaccess.pluginapi.converter.DataConverter;

/**
 * Entry point of a plugin module that specifies its core elements and makes it compatible with the main engine. It is
 * responsible for providing the plugin's configuration in form of {@link PluginConfig} and raw data into common object
 * format conversion mechanism in form of {@link DataConverter}.
 */
public interface PluginConnector {

    DataConverter getConverter();

    PluginConfig getConfig();

}
