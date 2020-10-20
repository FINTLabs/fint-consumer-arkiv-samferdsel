FROM gradle:4.10.3-jdk8-alpine as builder
USER root
COPY . .
ARG apiVersion
ARG buildFlags=""
RUN gradle --no-daemon ${buildFlags} -PapiVersion=${apiVersion} build

FROM gcr.io/distroless/java:8
ENV JAVA_TOOL_OPTIONS -XX:+ExitOnOutOfMemoryError
COPY --from=builder /home/gradle/build/deps/external/*.jar /data/
COPY --from=builder /home/gradle/build/deps/fint/*.jar /data/
COPY --from=builder /home/gradle/build/libs/fint-consumer-arkiv-samferdsel-*.jar /data/fint-consumer-arkiv-samferdsel.jar
CMD ["/data/fint-consumer-arkiv-samferdsel.jar"]
