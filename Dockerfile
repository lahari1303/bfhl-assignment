# ---- Build stage ----
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /app

# Copy everything
COPY . .

# Ensure Maven wrapper has execute permission
RUN chmod +x ./mvnw

# Build the project
RUN ./mvnw -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-focal

WORKDIR /app
# Copy the generated jar (any version) to app.jar
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
