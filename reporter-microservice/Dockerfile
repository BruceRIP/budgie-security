FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 3005
#ARG JAR_FILE
#COPY ${JAR_FILE} reporter-microservice.jar
ADD target/reporter-microservice-1.0-ALPHA-fat.jar reporter-microservice.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=test","/reporter-microservice.jar"]:
