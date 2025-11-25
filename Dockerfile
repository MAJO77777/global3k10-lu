# Imagen para build
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY . .

RUN ./gradlew clean build -x test

# Imagen final
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/ExamenMercado-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
