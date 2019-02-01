FROM java:8-alpine
 
ADD testclient-1.0-SNAPSHOT.jar app.jar
 
ENV JAVA_HOME /usr/java/jdk1.8.0_121
ENV PATH $PATH:$JAVA_HOME/bin
 
CMD ["java","-jar","app.jar"]

