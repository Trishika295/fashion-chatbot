FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 10000
CMD ["java", "-jar", "target/fashion-chatbot-0.0.1-SNAPSHOT.jar"]