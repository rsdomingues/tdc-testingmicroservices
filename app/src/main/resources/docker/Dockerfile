FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp
ADD checkout.jar app.jar
RUN sh -c 'touch /app.jar
ENV JAVA_OPTS="-Xms512m -Xmx512m"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]