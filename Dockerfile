FROM openjdk:8-jdk-alpine

# 빌드목적
ARG JAR_FILENAME='oauth2-0.0.1-SNAPSHOT.jar'
ARG PACKAGE_JAR_LOCATION=build/libs/${JAR_FILENAME}

# 실행목적
# docker run --e 로 오버라이딩
ENV SERVER_PORT=8081

ADD ${PACKAGE_JAR_LOCATION} app.jar

EXPOSE ${SERVER_PORT}:8081
ENTRYPOINT java ${COMMAND_LINE_ARGS_BEFORE} -jar ./app.jar --spring.profiles.active=docker