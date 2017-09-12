FROM java:8-jre

ADD ./target/form-service.jar /app/
CMD ["java", "-Xmx200m", "-Xdebug", "-Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n", "-jar", "/app/form-service.jar"]

EXPOSE 8080