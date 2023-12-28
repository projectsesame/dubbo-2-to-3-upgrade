FROM openjdk:11

COPY target/dubbo-spring-boot-consumer-1.0-SNAPSHOT.jar /app.jar

EXPOSE 8080

ENTRYPOINT java -XX:+PrintFlagsFinal  -XX:+UnlockExperimentalVMOptions  $JAVA_OPTS -jar app.jar