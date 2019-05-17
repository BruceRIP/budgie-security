PATH_BILLERS=/Users/bruno.rivera/Development/GitHub/budgie-security
echo developerRIP2328 | sudo mongod --bind_ip_all &
#echo developerRIP2328 | sudo activemq start
sleep 10
java -jar $PATH_BILLERS/eureka-server/target/eureka-server-1.0-RELEASE-fat.jar &
java -jar $PATH_BILLERS/cloud-config-server/target/cloud-config-server-1.0-RELEASE.jar &
sleep 20
java -jar $PATH_BILLERS/zuul-edge-service/target/zuul-edge-service-1.0-RELEASE-fat.jar &
sleep 10
java -jar $PATH_BILLERS/packages-microservice/target/packages-microservice-1.0-RELEASE-fat.jar &
java -jar $PATH_BILLERS/accounts-microservice/target/accounts-microservice-1.0-RELEASE-fat.jar &
java -jar $PATH_BILLERS/security-oauth-sso-microservice/target/security-oauth-sso-microservice-1.0-RELEASE-fat.jar &
java -jar $PATH_BILLERS/register-security-microservice/target/register-security-microservice-0.0.1-SNAPSHOT-fat.jar &
java -jar $PATH_BILLERS/reporter-microservice/target/reporter-microservice-1.0-ALPHA-fat.jar &
