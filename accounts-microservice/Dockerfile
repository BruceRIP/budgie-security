FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 3002
#ARG JAR_FILE
#COPY ${JAR_FILE} accounts-microservice.jar
ADD target/accounts-microservice-1.0-RELEASE-fat.jar accounts-microservice.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test","/accounts-microservice.jar"]:
