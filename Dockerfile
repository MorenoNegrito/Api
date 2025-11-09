# ============================================
# Stage 1: Build
# ============================================
FROM eclipse-temurin:17-jdk-alpine AS build

# Metadatos
LABEL maintainer="tu-email@example.com"
LABEL description="API Desarrolladores - Spring Boot Application"

WORKDIR /app

# Copiar archivos de Maven wrapper
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución y descargar dependencias (cache layer)
RUN chmod +x ./mvnw && \
    ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src src

# Compilar aplicación (sin tests para deploy más rápido)
RUN ./mvnw clean package -DskipTests && \
    mv target/*.jar target/app.jar

# ============================================
# Stage 2: Runtime
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar herramientas de debugging (opcional, remover en producción estricta)
RUN apk add --no-cache curl

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR desde stage de build
COPY --from=build /app/target/app.jar app.jar

# Exponer puerto (informativo, Render usa $PORT)
EXPOSE 8080

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Health check (opcional, útil para Docker Compose local)
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Comando de inicio con configuración dinámica del puerto
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]