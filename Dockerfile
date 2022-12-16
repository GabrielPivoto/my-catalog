FROM openjdk
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} MyCatalog-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/MyCatalog-0.0.1-SNAPSHOT.jar"]