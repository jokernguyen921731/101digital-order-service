FROM adoptopenjdk:11-jre-openj9 as base-jdk

RUN mkdir -p app
WORKDIR app

FROM base-jdk as base-plugin

FROM base-plugin
ADD src/main/resources src/main/resources
COPY target/*.jar app/app.jar

ENTRYPOINT ["java", "-jar", "app/app.jar"]