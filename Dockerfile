FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/rubikscube-1.0-exec.jar
ADD ${JAR_FILE} rubikscube-1.0-exec.jar
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/rubikscube-1.0-exec.jar"]