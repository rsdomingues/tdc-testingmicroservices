FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD target/checkout.jar /app.jar
RUN sh -c 'touch /app.jar'
RUN sh -c 'touch /newrelic.yml'
RUN sh -c 'touch /newrelic.jar'
ENV JAVA_OPTS="-Xms512m -Xmx512m"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
