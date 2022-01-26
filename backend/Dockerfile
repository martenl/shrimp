FROM openjdk:13
COPY target/backend-0.0.1-SNAPSHOT.jar /usr/app/app.jar
COPY src/main/resources/application.properties /usr/app/application.properties
EXPOSE 8089
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]