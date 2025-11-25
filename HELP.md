# Docker para FormularioApp
Este proyecto está configurado para ejecutarse en Docker con Oracle Database incluido.

## Requisitos Previos

- Docker Desktop instalado y ejecutándose
- Docker Compose v3.8 o superior
- Al menos 4GB de RAM disponible para Oracle

## Inicio Rápido

### 1. Construir y levantar los servicios

```bash
docker-compose up --build
```

### 2. Detener los servicios

```bash
docker-compose down
```

## Acceder a Oracle desde la línea de comandos

```bash
docker exec -it formulario-oracle sqlplus daniel/daniel12@FREEPDB1
```

## Variables de Entorno

Las variables de entorno están definidas en `docker-compose.yml`. Pueden modificarse según tus necesidades:

- **Base de datos**: Cambiar `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
- **Mailtrap**: Actualizar credenciales de `SPRING_MAIL_USERNAME` y `SPRING_MAIL_PASSWORD`
- **Puerto**: Modificar `SERVER_PORT` y el mapeo de puertos en `docker-compose.yml`

## Persistencia de Datos

Los datos de Oracle se almacenan en un volumen Docker llamado `oracle-data`. Esto significa que los datos persisten incluso si detienes y eliminas los contenedores (a menos que uses `docker-compose down -v`).

## Caso de que puerto 4000 ya está en uso

Cambia el puerto en `docker-compose.yml`:

```yaml
ports:
  - "4001:4000"  # Usa 4001 en lugar de 4000
```