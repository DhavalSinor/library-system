FROM openjdk:17-jdk-slim
LABEL authors="Dhaval Shah"
MAINTAINER "dhaval777"
COPY target/library-0.0.1-SNAPSHOT.jar library-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "library-0.0.1-SNAPSHOT.jar"]


