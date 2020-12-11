FROM java:8
VOLUME /resources/static
ADD out/artifacts/MicroServicesMongoDBTasks_jar/MicroServicesMongoDBTasks.jar app1.jar
EXPOSE 8080
RUN bash -c 'touch /app1.jar'
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/records", "-Djava.security.egd=file:/dev/./urandom","-jar","/app1.jar"]