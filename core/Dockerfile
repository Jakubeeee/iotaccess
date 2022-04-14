FROM openjdk:18
RUN mkdir /opt/artifact
RUN mkdir /opt/iotaccess
RUN mkdir /opt/iotaccess/pluginimport
RUN mkdir /opt/iotaccess/configimport
RUN mkdir /opt/iotaccess/configexport
COPY target/core-1.0-SNAPSHOT.jar /opt/artifact/core.jar
EXPOSE 8080 1044
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:1044"
ARG CORE_ARGS
ENV CORE_ARGS ${CORE_ARGS}
CMD java --enable-preview -jar /opt/artifact/core.jar $CORE_ARGS