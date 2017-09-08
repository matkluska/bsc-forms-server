FROM java:8-jre

ADD ./target/config-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config-service.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8080/health || exit 1

EXPOSE 8080