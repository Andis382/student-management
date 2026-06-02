# ============================================================
# Dockerfile shumë-fazësh për "Sistemi i Menaxhimit të Studentëve"
# Përdoret për ndërtimin e imazhit që deploy-ohet në OpenShift.
# ============================================================

# Faza 1: Ndërtimi i aplikacionit me Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Faza 2: Imazhi final vetëm me JRE dhe artifaktin .jar
FROM eclipse-temurin:17-jre
WORKDIR /app
# Kopjon .jar-in e ekzekutueshëm të modulit web
COPY --from=build /app/web/target/web-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
