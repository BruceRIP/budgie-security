FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 3001
#ARG JAR_FILE
#COPY ${JAR_FILE} security-oauth-microservice.jar
ADD target/security-oauth-microservice-1.0-RELEASE-fat.jar security-oauth-microservice.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test","/security-oauth-microservice.jar"]:
