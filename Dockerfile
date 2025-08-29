# ---- Build stage ----
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /app

# Copy Maven wrapper & project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Ensure Maven wrapper has execute permission
RUN chmod +x ./mvnw

# Build the project
RUN ./mvnw -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copy the generated jar (rename to app.jar)
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render replaces this with PORT env var)
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]
