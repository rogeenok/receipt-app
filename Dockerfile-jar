FROM anapsix/alpine-java
COPY ./server/target/server-1.0.jar /home/server.jar
ENTRYPOINT ["java","-jar","/home/server.jar"]
EXPOSE 8090