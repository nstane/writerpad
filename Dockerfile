FROM openjdk:8
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} writerpad.jar
ENTRYPOINT ["java","-jar","/writerpad.jar"]