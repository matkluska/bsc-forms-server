FROM java:8-jre
MAINTAINER Mateusz Kluska

ADD ./target/user-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/user-service.jar"]

EXPOSE 8083