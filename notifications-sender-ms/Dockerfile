FROM openjdk:17-alpine
EXPOSE 8082
RUN mkdir /app
COPY build/libs/sender-1.1.0.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]