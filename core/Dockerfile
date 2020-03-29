FROM adoptopenjdk/openjdk14
RUN mkdir /opt/app
RUN mkdir /opt/pluginimport
COPY target/core-1.0-SNAPSHOT.jar /opt/app/core.jar
EXPOSE 8080 1044
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:1044"
ARG CORE_ARGS
ENV CORE_ARGS ${CORE_ARGS}
CMD java --enable-preview -jar /opt/app/core.jar $CORE_ARGS