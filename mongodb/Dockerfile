FROM mongo:3.4.7

MAINTAINER Mateusz Kluska

ENV AUTH yes
ENV STORAGE_ENGINE wiredTiger
ENV JOURNALING yes

ADD run.sh /run.sh
ADD set_mongodb_password.sh /set_mongodb_password.sh

RUN ["chmod", "+x", "/run.sh"]

CMD ["/run.sh"]