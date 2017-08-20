FROM java:8-jre
MAINTAINER Mateusz Kluska

ADD ./target/gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 8080