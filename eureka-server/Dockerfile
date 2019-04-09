FROM library/fedora
RUN dnf install -y java-1.8.0-openjdk-devel.x86_64 && dnf install -y git-all
#VOLUME /tmp
EXPOSE 8761
#ARG JAR_FILE
#COPY ${JAR_FILE} eureka-server.jar
ADD target/eureka-server-1.0-RELEASE-fat.jar eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]:
