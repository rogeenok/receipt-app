# -- FIRST IMAGE WITH SRC -- 
FROM jimador/docker-jdk-8-maven-node
MAINTAINER Igor Konovalov "rogee.nok@gmail.com"
COPY client ./home/receipt-app/src/client
COPY server ./home/receipt-app/src/server
COPY pom.xml ./home/receipt-app/src/
# MAKE BUILD SRC
# --------------
RUN mvn -f ./home/receipt-app/src/pom.xml clean package

# -- SECOND IMAGE WITH JAR ONLY
FROM anapsix/alpine-java
COPY --from=0 /home/receipt-app/src/server/target/server-1.0.jar /home/receipt-app.jar
ENTRYPOINT ["java","-jar","/home/receipt-app.jar"]
EXPOSE 8090