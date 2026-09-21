# DevSecOps Java Microservice

Este proyecto fue realizado como parte de una práctica de DevSecOps.

La aplicación es un microservicio desarrollado con Java, Spring Boot y Maven. El microservicio permite realizar operaciones básicas sobre productos, como crear, consultar, actualizar y eliminar.

## Tecnologías utilizadas

* Java 17
* Spring Boot
* Maven
* JUnit
* Mockito
* JaCoCo
* Jenkins
* SonarQube
* Docker
* Docker Hub
* H2 Database

## Pipeline

El archivo `Jenkinsfile` se encuentra en la raíz del proyecto.

El pipeline realiza las siguientes etapas:

1. Compila el proyecto con Maven.
2. Ejecuta las pruebas unitarias con JUnit y Mockito.
3. Genera cobertura de código con JaCoCo.
4. Analiza el código con SonarQube.
5. Genera el archivo FAT JAR.
6. Construye una imagen Docker.
7. Publica la imagen en Docker Hub.

El flujo general es:

```text
GitHub
   |
   v
Jenkins
   |
   v
Build
   |
   v
Testing + JaCoCo
   |
   v
SonarQube
   |
   v
FAT JAR
   |
   v
Docker Build
   |
   v
Docker Hub
```

## Docker

El proyecto contiene un `Dockerfile` que permite generar una imagen Docker utilizando el archivo JAR creado por Maven.

Para construir la imagen manualmente:

```bash
docker build -t devsecops-java-microservice .
```

Para ejecutar el contenedor:

```bash
docker run -p 8085:8080 devsecops-java-microservice
```

Después se puede probar el microservicio en:

```text
http://localhost:8085/api/productos
```

## Docker Compose

Para las pruebas del pipeline utilicé Jenkins y SonarQube ejecutándose localmente con Docker.

El archivo utilizado para levantar la infraestructura se dejó dentro de:

```text
/resources/docker-compose.yml
```

También se incluye el archivo utilizado para preparar Jenkins con Docker CLI.

## Pruebas

Las pruebas se pueden ejecutar con:

```bash
./mvnw clean test
```

JaCoCo genera el reporte de cobertura dentro de:

```text
target/site/jacoco/
```

## Generar el FAT JAR

Para generar el artefacto de la aplicación:

```bash
./mvnw clean package
```

El archivo generado queda dentro de:

```text
target/
```

Este proyecto fue realizado con fines de aprendizaje para practicar un pipeline básico de Integración Continua y Entrega Continua utilizando Jenkins, SonarQube, Docker y Docker Hub.
