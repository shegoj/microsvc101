FROM zenika/alpine-maven:3-jdk-8 
COPY . /usr/src/app  
 
RUN mvn -f /usr/src/app/pom.xml clean package

 
COPY target/*.jar /usr/app/direction-0.0.1-SNAPSHOT.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/direction-0.0.1-SNAPSHOT.jar"] 

