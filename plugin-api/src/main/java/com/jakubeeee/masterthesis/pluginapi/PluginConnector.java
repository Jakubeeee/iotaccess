package com.jakubeeee.masterthesis.pluginapi;

import com.jakubeeee.masterthesis.pluginapi.config.PluginConfig;
import com.jakubeeee.masterthesis.pluginapi.converter.DataConverter;

/**
 * Entry point of a plugin module that specifies its core elements and makes it compatible with the main engine.
 */
public interface PluginConnector {

    DataConverter getConverter();

    PluginConfig getConfig();

}
