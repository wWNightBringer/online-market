# Use an official Ubuntu as the base image
FROM openjdk:17-alpine

# Download and install Gradle
ENV GRADLE_VERSION=8.2.1
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip && \
    ln -s /opt/gradle/gradle-${GRADLE_VERSION} /opt/gradle/latest

ENV GRADLE_HOME=/opt/gradle/latest
ENV PATH=$PATH:$GRADLE_HOME/bin
ENV CONSUL_HOST=consul

# Set working directory
WORKDIR /app

# Copy the Gradle project files
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Build the application using Gradle
RUN gradle clean build

# Copy the generated JAR file based on the version
COPY build/libs/user-0.0.1-SNAPSHOT.jar /app/user.jar

# Start the application
ENTRYPOINT ["java", "-Xmx256m", "-Xss256k", "-XX:MetaspaceSize=100m", "-jar", "user.jar"]
