# Sistema de gestión de tareas

Aplicación web para administrar las tareas de un equipo de trabajo: registrar,
consultar, modificar, eliminar, asignar responsable, establecer estado y
prioridad, y filtrar por estado o responsable.

Evidencia 3 · Desarrollo Web Integral · UTCH · IDGS91N

## Arquitectura

Cliente-servidor estructurada en capas, integrada por una API REST.

    frontend/   Interfaz con Angular 22            (puerto 4200)
    backend/    API REST con Spring Boot 4.1.1     (puerto 8080)
                PostgreSQL 17                      (puerto 5432)

Capas del backend (paquete raíz `com.tareas`):

    controller/   recibe la petición HTTP y responde
    service/      reglas de negocio
    repository/   acceso a datos (JPA)
    model/        entidades que reflejan las tablas
    dto/          lo que viaja como JSON hacia el cliente
    exception/    manejo central de errores
    util/         validaciones

## Requisitos

    Java 21 · Maven (wrapper incluido) · Node.js 22.23.2 · Angular CLI 22 · PostgreSQL 17

## Cómo levantarlo

    cd backend  && .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
    cd frontend && npm install --legacy-peer-deps && ng serve

`application-dev.properties` no se versiona (lleva credenciales de la base de
datos); cada integrante crea el suyo.

## Flujo de trabajo con git

    master                         producción: solo recibe releases desde develop
      └── develop                  integración: rama por defecto del repositorio
            └── feat/<iniciales>/<qué-hace>     una rama por integrante y tarea

1. Antes de crear una rama: `git checkout develop` y `git pull`.
2. Commits pequeños con Conventional Commits: `feat(tareas): agrega registro de tarea`.
3. Pull Request hacia `develop`, revisado y aprobado por **otro** integrante.
4. Nadie integra su propio Pull Request.

## Equipo y distribución del trabajo

| Integrante | Actividad asignada | Rama |
| :--- | :--- | :--- |
| Aram Humberto Alemán Cadenas | Por definir | Por definir |
| Ramiro Héctor Aragón Martínez | Estructura inicial del repositorio · Por definir | Por definir |
| Luis Joel Torres Castellanos | Por definir | Por definir |
| Luis Carlos Gutiérrez Ramos | Por definir | Por definir |
| Diego Emilio Rodríguez Falcón | Por definir | Por definir |
