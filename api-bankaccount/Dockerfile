FROM openjdk:8-jdk-alpine
ADD target/*.jar /usr/share/api-bankaccount.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/share/api-bankaccount.jar"]