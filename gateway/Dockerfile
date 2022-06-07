FROM openjdk:13
COPY target/gateway-0.0.1-SNAPSHOT.jar /usr/app/app.jar
COPY src/main/resources/application.yml /usr/app/application.yml
EXPOSE 8089
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]