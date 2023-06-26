FROM openjdk:17.0.1-jdk-slim
ARG JAR_FILE=build/libs/equino-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

# using multistage docker build
# ref: https://docs.docker.com/develop/develop-images/multistage-build/
    
# temp container to build using gradle
#FROM gradle:7.6.1-jdk-alpine AS TEMP_BUILD_IMAGE
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#COPY build.gradle settings.gradle $APP_HOME
  
#COPY gradle $APP_HOME/gradle
#COPY --chown=gradle:gradle . /home/gradle/src
#USER root
#RUN chown -R gradle /home/gradle/src
    
#RUN gradle build -x test || return 0
#COPY . .
#RUN gradle clean build -x test
    
# actual container
#FROM openjdk:17.0.1-jdk-slim
#ENV ARTIFACT_NAME=equino-0.0.1-SNAPSHOT.jar
#ENV APP_HOME=/usr/app/
    
#WORKDIR $APP_HOME
#COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
    
#EXPOSE 8080
#ENTRYPOINT exec java -jar ${ARTIFACT_NAME}