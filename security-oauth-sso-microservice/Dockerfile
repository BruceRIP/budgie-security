FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 3040
#ARG JAR_FILE
#COPY ${JAR_FILE} security-oauth-sso-microservice.jar
ADD target/security-oauth-sso-microservice-1.0-RELEASE-fat.jar security-oauth-sso-microservice.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test","/security-oauth-sso-microservice.jar"]:
