# cache as most as possible in this multistage dockerfile.
FROM maven:3.6-alpine as DEPS

WORKDIR /opt/app
COPY application/pom.xml application/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY infrastructure/pom.xml infrastructure/pom.xml
COPY rest/pom.xml rest/pom.xml
COPY security/pom.xml security/pom.xml
COPY test-data/pom.xml test-data/pom.xml

# you get the idea:
# COPY moduleN/pom.xml moduleN/pom.xml

COPY pom.xml .
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline

# if you have modules that depends each other, you may use -DexcludeArtifactIds as follows
# RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=module1

# Copy the dependencies from the DEPS stage with the advantage
# of using docker layer caches. If something goes wrong from this
# line on, all dependencies from DEPS were already downloaded and
# stored in docker's layers.
FROM maven:3.6.1-jdk-11 as BUILDER
WORKDIR /opt/app
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/app/ /opt/app
COPY application/src /opt/app/application/src
COPY domain/src /opt/app/application/src
COPY infrastructure/src /opt/app/application/src
COPY rest/src /opt/app/application/src
COPY security/src /opt/app/application/src
COPY test-data/src /opt/app/application/src

# use -o (--offline) if you didn't need to exclude artifacts.
# if you have excluded artifacts, then remove -o flag
RUN mvn -B -e clean install -DskipTests=true

# At this point, BUILDER stage should have your .jar or whatever in some path
FROM openjdk:11-jre-slim
WORKDIR /opt/app
COPY --from=builder /opt/app/application/target/culture-api-application-1.0-SNAPSHOT.jar .
EXPOSE 8080
CMD [ "java", "-jar", "/opt/app/culture-api-application-1.0-SNAPSHOT.jar" ]