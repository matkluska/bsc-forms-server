FROM java:8-jre

ADD ./target/reply-stats-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/reply-stats-service.jar"]

EXPOSE 8080