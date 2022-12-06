FROM node:18.12.1
RUn apt-get update
RUN apt-get install libc6-i386 libc6-x32
RUN wget https://download.oracle.com/java/19/latest/jdk-19_linux-x64_bin.deb
RUN apt-get install -y ./jdk-19_linux-x64_bin.deb

RUN \
    curl -L https://services.gradle.org/distributions/gradle-7.6-bin.zip -o gradle-7.6-bin.zip && \
    unzip gradle-7.6-bin.zip && \
    rm gradle-7.6-bin.zip

ENV JAVA_HOME /usr/lib/jvm/jdk-19
ENV PATH $JAVA_HOME/bin:$PATH $JAVA_HOME/bin

ENV GRADLE_HOME=/gradle-7.6
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /ElevatorSystemControl
COPY . /ElevatorSystemControl

RUN gradle build

EXPOSE 8080
ENTRYPOINT exec java -jar app/elevator-system-control-1.0.jar

