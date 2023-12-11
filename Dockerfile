# temp build
FROM docker.io/gradle:8.0.2-jdk AS TEMP_BUILD
ARG SKIP_TESTS=false
# Copy project files
COPY build.gradle settings.gradle /home/gradle/src/
COPY src /home/gradle/src/src
COPY gradle /home/gradle/src/gradle
COPY config /home/gradle/src/config
COPY monitoring /home/gradle/src/monitoring
WORKDIR /home/gradle/src
RUN if [ "$SKIP_TESTS" = "true" ]; then \
    gradle build --no-daemon -x test; \
  else \
    gradle build --no-daemon; \
  fi

# build image
FROM openjdk:17-alpine
RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot
USER nonroot
WORKDIR /app
COPY --from=TEMP_BUILD /home/gradle/src/build/libs/*.jar /app/broker-adapter.jar
ENTRYPOINT ["java", "-jar", "/app/broker-adapter.jar"]
