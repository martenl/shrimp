FROM openjdk:13
COPY target/frontend-0.0.1-SNAPSHOT.jar /usr/app/app.jar
EXPOSE 8088
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]