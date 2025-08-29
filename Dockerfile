# ---- Build stage ----
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /app

COPY . .
RUN ./mvnw -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-focal

WORKDIR /app
# Copy any jar from target folder
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
