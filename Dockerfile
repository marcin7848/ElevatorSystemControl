FROM gradle:7.6.0-jdk19
RUN apt-get update
RUN apt-get install -y nodejs npm


WORKDIR /ElevatorSystemControl
COPY . /ElevatorSystemControl

RUN gradle build

EXPOSE 8080
ENTRYPOINT exec java -jar app/elevator-system-control-1.0.jar

