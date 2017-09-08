FROM java:8-jre

ADD ./target/form-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/form-service.jar"]

EXPOSE 8080