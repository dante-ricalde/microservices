FROM tomcat:8.5-alpine
# If you want to write to the filesystem, what's going to happen is by default /tmp is going to be
# mapped to your dockerHost/var/lib/docker/volume in there, so it'll be persistent
VOLUME /tmp
COPY springboot_docker_tomcat_mongodb-2.0.war /usr/local/tomcat/webapps/app.war
# We execute this to give it a modification timestamp to the war file because Tomcat is going to need to know that
RUN sh -c 'touch /usr/local/tomcat/webapps/app.war'
# This ENTRY POINT is the command that's going to execute as soon as you run the container
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/tomcat/webapps/app.war" ]