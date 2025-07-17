FROM openjdk:21-slim  AS build
LABEL maintainer="Illary Huaylupo <illaryhs@gmail.com>"
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
RUN mkdir -p /target/dependency && (cd /target/dependency; jar -xf /app.jar)

FROM openjdk:21-slim
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/META-INF         /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib     /app/lib
ENTRYPOINT ["java","-cp","/app:/app/lib/*","com.optimagrowth.license.LicenseServiceApplication"]
