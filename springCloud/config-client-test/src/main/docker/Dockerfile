FROM java:openjdk-8-jre

ENV SLEEP=2

# add directly the jar
ADD *.jar /app.jar

COPY wait-for-config-server.sh .
RUN chmod a+x wait-for-config-server.sh

# EXPOSE 8080 5701/udp
CMD echo "The application will start in ${SLEEP}s..." && \
	sleep ${SLEEP} && \
	echo "Echoing JAVA_OPTS ${JAVA_OPTS}" && \
	java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar