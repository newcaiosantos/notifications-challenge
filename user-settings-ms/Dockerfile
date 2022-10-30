# Multi-Stage Build
# (Não adotado por levar muito tempo no build em Mac M1 com platform linux/amd64)
#
# FROM gradle:7.5.1-jdk17-alpine AS build
# COPY --chown=gradle:gradle . /home/gradle/src
# WORKDIR /home/gradle/src
# RUN gradle build --no-daemon

# FROM openjdk:17-alpine
# EXPOSE 8080
# RUN mkdir /app
# COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
# ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]

FROM openjdk:17-alpine
EXPOSE 8080
RUN mkdir /app
COPY build/libs/usersettings-1.1.0.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]