FROM maven:3.6.0-jdk-11-slim AS build

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests


FROM openjdk:11-jre-slim-buster


ARG JAR_FILE=/home/app/target/admissionAPI-0.0.1-SNAPSHOT.jar
COPY --from=build  ${JAR_FILE} admissionAPI.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/admissionAPI.jar"]