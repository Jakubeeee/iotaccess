module com.jakubeeee.iotaccess.randomnumberpluginspi {

    requires static com.jakubeeee.iotaccess.pluginapi;

    requires static com.fasterxml.jackson.annotation;
    requires static com.fasterxml.jackson.databind;
    requires static lombok;

    provides com.jakubeeee.iotaccess.pluginapi.PluginConnector
            with com.jakubeeee.iotaccess.randomnumberpluginspi.RandomNumberPluginSPIConnector;

}