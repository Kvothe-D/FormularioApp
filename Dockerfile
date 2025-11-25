# Dockerfile para FormularioApp
# Etapa 1: Build - Compilar la aplicación con Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# Establecer directorio de trabajo para la etapa de compilación
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Descargar dependencias (caché de capas)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Runtime - Imagen final ligera solo con JRE
FROM eclipse-temurin:21-jre-alpine

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Establecer directorio de trabajo para la etapa de ejecución
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/FormularioApp-*.jar app.jar

# Exponer el puerto
EXPOSE 4000

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:4000/ || exit 1

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]