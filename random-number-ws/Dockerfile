FROM adoptopenjdk/openjdk14
RUN mkdir /opt/artifact
COPY target/random-number-ws-1.0-SNAPSHOT.jar /opt/artifact/random-number-ws.jar
EXPOSE 8081 1045
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:1044"
ARG RANDOM_NUMBER_WS_ARGS
ENV RANDOM_NUMBER_WS_ARGS ${RANDOM_NUMBER_WS_ARGS}
CMD java --enable-preview -jar /opt/artifact/random-number-ws.jar $RANDOM_NUMBER_WS_ARGS