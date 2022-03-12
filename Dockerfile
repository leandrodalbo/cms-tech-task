FROM openjdk:11
ADD target/content-management-build.jar content-management-build.jar
ENTRYPOINT ["java", "-jar","content-management-build.jar"]
EXPOSE 8080