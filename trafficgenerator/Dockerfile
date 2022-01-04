FROM openjdk:13
COPY target/trafficgenerator-0.0.1-SNAPSHOT.jar /usr/app/app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]