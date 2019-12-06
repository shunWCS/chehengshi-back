FROM openjdk:8-jre

RUN mkdir /app

COPY che_heng_shi-0.0.1-SNAPSHOT.jar /app/

CMD nohup java -jar che_heng_shi-0.0.1-SNAPSHOT.jar > ./console_output &

EXPOSE 443