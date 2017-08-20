FROM java:8-jre
MAINTAINER Mateusz Kluska

ADD ./target/discovery-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/discovery-service.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8082/health || exit 1

EXPOSE 8082