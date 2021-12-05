FROM ubuntu:latest

RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
RUN apt-get install -y maven

WORKDIR /app
COPY . /app
RUN mvn clean install

CMD java -jar /app/target/numberGenerator-0.0.1-SNAPSHOT.jar