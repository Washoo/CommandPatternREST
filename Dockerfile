FROM openjdk:11-jre-slim
WORKDIR /opt
ADD target/demo-pattern-heros-*SNAPSHOT.jar /opt/app.jar

RUN sh -c 'touch /opt/app.jar'

ENTRYPOINT ["java", "-Dloader.path=/opt", "-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]