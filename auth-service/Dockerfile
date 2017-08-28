FROM java:8-jre
MAINTAINER Mateusz Kluska

ADD ./target/auth-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service.jar"]

EXPOSE 8083