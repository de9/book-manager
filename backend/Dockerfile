FROM openjdk:14-alpine
COPY build/libs/backend-*-all.jar backend.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "backend.jar"]