
########Maven build stage########
FROM maven:3.6-jdk-11 as maven_build
WORKDIR /app

#copy pom
COPY pom.xml .

#copy source
COPY src ./src

# build the app, use cache so dependencies are only download once
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

# unpack jar and save files in /target/docker-packaging
RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xf ../*.jar


########JRE run stage########
FROM openjdk:8-jdk-alpine
WORKDIR /app

#copy built app layer by layer
ARG DOCKER_PACKAGING_DIR=/app/target/docker-packaging
#The COPY --from=maven_build line copies just the built artifact from the previous stage into this new stage.
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/lib /app/lib
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/classes /app/classes
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/META-INF /app/META-INF

#run the app. use the application’s own main class is faster than using jar
ENTRYPOINT ["java","-cp",".:classes:lib/*","gifengine.GIFEngineApplication"]