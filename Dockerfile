# ---- Build stage ----
FROM eclipse-temurin:17-jdk-focal AS build

WORKDIR /app

<<<<<<< HEAD
# Copy everything
COPY . .

# Ensure Maven wrapper has execute permission
RUN chmod +x ./mvnw

# Build the project
=======
COPY . .
>>>>>>> 8a79e64cdcf5240e211ad4a62bfe3b3101a4ca1b
RUN ./mvnw -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-focal

WORKDIR /app
<<<<<<< HEAD
# Copy the generated jar (any version) to app.jar
=======
# Copy any jar from target folder
>>>>>>> 8a79e64cdcf5240e211ad4a62bfe3b3101a4ca1b
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
