FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 8080
#ARG JAR_FILE
#COPY ${JAR_FILE} zuul-edge-service.jar
ADD target/zuul-edge-service-0.0.1-SNAPSHOT-fat.jar zuul-edge-service.jar
ENTRYPOINT ["java","-jar","/zuul-edge-service.jar"]:
