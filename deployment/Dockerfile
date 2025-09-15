FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser
COPY ./applications/app-service/build/libs/reportes.jar reportes.jar
RUN chmod 444 reportes.jar && chown appuser:appuser reportes.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar reportes.jar"]
