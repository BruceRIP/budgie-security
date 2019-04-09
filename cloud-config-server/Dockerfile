FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 5550
#ARG JAR_FILE
#COPY ${JAR_FILE} cloud-config-server.jar
ADD target/cloud-config-server-1.0-RELEASE.jar cloud-config-server.jar
ENTRYPOINT ["java","-jar","/cloud-config-server.jar"]:
