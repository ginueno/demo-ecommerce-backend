FROM eclipse-temurin:21-jdk as build
COPY . /app
WORKDIR /app
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./mvnw
RUN chmod +x ./mvnw
RUN ./mvnw --no-transfer-progress clean package -DskipTests
RUN mv -f target/*.jar app.jar

FROM eclipse-temurin:21-jre
ARG PORT
ENV PORT=${PORT}
COPY --from=build /app/app.jar .
RUN useradd runtime
USER runtime
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]