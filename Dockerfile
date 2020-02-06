# OpenJDK JRE
FROM openjdk:13.0.2-oracle

# copy WAR into image
COPY build/libs/bank-*.jar /app.war

# run application with this command line (default = prod; dev = dev with container)
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=dev", "/app.war"]