# Sistema de Gestión de Tareas

Aplicación web integral para la administración colaborativa del ciclo de vida de tareas en un equipo de trabajo. Permite registrar nuevas tareas, consultar listados y detalles individuales, modificar datos, eliminar tareas, asignar integrantes responsables, gestionar estados y niveles de prioridad, y aplicar filtros de búsqueda dinámicos.

> **Proyecto Académico:** Evidencia 3 · Desarrollo Web Integral · UTCH · IDGS91N

---

## 👥 Equipo de Desarrollo y Distribución del Trabajo

| Integrante | Rama Git | Módulo / Actividad Desarrollada |
| :--- | :--- | :--- |
| **Aram Humberto Alemán Cadenas** | `feature/aram/consulta-tareas` | Consulta general y detallada de tareas (endpoints GET `/tarea` y `/tarea/{id}`, lógica de servicio y DTOs de respuesta). |
| **Diego Emilio Rodríguez Falcón** | `feature/modificar-tareas` | Modificación y edición de tareas existentes (endpoint PUT `/tarea/{id}`, validaciones y actualización en servicio). |
| **Luis Carlos Gutiérrez Ramos** | `feature/luis/eliminacion-tareas` | Eliminación de tareas (endpoint DELETE `/tarea/{id}` y desasignación/borrado en la capa de servicio). |
| **Luis Joel Torres Castellanos** | `feature/jt/estados-prioridades`<br>`feature/jt/filtros` | Gestión de estados y prioridades, y filtros dinámicos de tareas por responsable y estado (endpoints, DTOs y lógica de negocio). |
| **Ramiro Héctor Aragón Martínez** | `feature/ram/registro-asignacion`<br>`fix/ra/mvnw-ejecutable` | Arquitectura base del proyecto, modelos de entidad (`Tarea`, `Integrante`), repositorios JPA, registro y asignación de tareas. |

---

## 🛠️ Stack Tecnológico

El proyecto utiliza una arquitectura desacoplada cliente-servidor basada en las siguientes tecnologías:

### Backend
- **Lenguaje:** Java 21 LTS
- **Framework:** Spring Boot 4.1.1
- **Módulos:** Spring Web MVC, Spring Data JPA
- **Persistencia / ORM:** Hibernate
- **Utilidades:** Project Lombok
- **Gestor de dependencias y construcción:** Apache Maven (incluye Maven Wrapper `mvnw`)

### Frontend
- **Framework:** Angular 22
- **Lenguaje:** TypeScript 6.0
- **Librerías reactivas:** RxJS 7.8
- **Estilos y estructura:** HTML5 & CSS3
- **Herramienta de compilación:** Angular CLI 22

### Base de Datos
- **Motor:** PostgreSQL 17 (puerto por defecto `5432`)

### Control de Versiones
- **VCS:** Git & GitHub bajo convención de ramas (*GitFlow*) y commits semánticos (*Conventional Commits*).

---

## 📋 Requisitos Previos

Antes de instalar y ejecutar la aplicación, asegúrese de contar con las siguientes herramientas instaladas en su entorno:

1. **Java Development Kit (JDK):** Versión 21 o superior.
2. **Node.js:** Versión 22.x (recomendado v22.23.2) y **npm** (v10.x).
3. **Angular CLI:** Versión 22 (`npm install -g @angular/cli@22`).
4. **PostgreSQL:** Versión 17 en ejecución en el puerto `5432`.
5. **Git:** Para clonar y gestionar el repositorio.

---

## 🚀 Guía de Instalación y Ejecución

Siga estos pasos para clonar, configurar y levantar la aplicación en su entorno local:

### 1. Clonar el Repositorio
```bash
git clone https://github.com/ramsz9/Evidencia3.git
cd Evidencia3
```

### 2. Configurar la Base de Datos (PostgreSQL)
Abra su cliente de PostgreSQL (`psql`, pgAdmin o DBeaver) y cree la base de datos requerida para el proyecto:
```sql
CREATE DATABASE evidencia3;
```

### 3. Configurar Credenciales del Backend
El archivo `application-dev.properties` no se versiona por seguridad. Cree el archivo en la ruta `backend/src/main/resources/application-dev.properties` con sus credenciales locales:

```properties
spring.application.name=backend

# Conexión a la base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/evidencia3
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
*(Ajuste `spring.datasource.username` y `spring.datasource.password` según su configuración de PostgreSQL).*

### 4. Ejecutar el Backend (Spring Boot)
Abra una terminal en la carpeta `backend` y ejecute:

- **En Windows (PowerShell / CMD):**
  ```powershell
  cd backend
  .\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
  ```
- **En Linux / macOS:**
  ```bash
  cd backend
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
  ```

El servidor backend iniciará en: `http://localhost:8080`

### 5. Instalar Dependencias y Ejecutar el Frontend (Angular)
Abra una segunda terminal en la carpeta `frontend` y ejecute:

```bash
cd frontend
npm install --legacy-peer-deps
ng serve
```
*(o alternativamente `npm start`)*

La aplicación web estará disponible en su navegador en: `http://localhost:4200`

---

## 🏛️ Arquitectura del Software

Arquitectura cliente-servidor estructurada en capas, comunicada mediante una API REST:

```text
frontend/ (Angular 22 - Puerto 4200)
    │
    ▼ [Peticiones HTTP / JSON]
backend/  (Spring Boot 4.1.1 - Puerto 8080)
    │
    ▼ [Conexión JDBC / JPA]
PostgreSQL 17 (Puerto 5432 - BD: evidencia3)
```

### Estructura de Capas del Backend (`com.tareas`)
- `controller/`: Expone los endpoints REST y gestiona las peticiones y respuestas HTTP.
- `service/`: Contiene la lógica y reglas de negocio del sistema.
- `repository/`: Interfaces de acceso a datos mediante Spring Data JPA.
- `model/`: Entidades JPA que mapean las tablas de la base de datos (`Tarea`, `Integrante`).
- `dto/`: Objetos de transferencia de datos enviados y recibidos en formato JSON.
- `exception/`: Control y captura centralizada de errores y excepciones HTTP.
- `util/`: Validaciones auxiliares de entrada y datos.

---

## 🌿 Flujo de Trabajo con Git

El equipo adopta un flujo de integración continuo basado en GitFlow simplificado:

```text
master                         producción: solo recibe releases desde develop
  └── develop                  integración: rama activa y por defecto del proyecto
        └── feat/<iniciales>/<qué-hace>     ramas de desarrollo individual por integrante
```

### Reglas de Integración:
1. **Actualización previa:** Antes de crear una rama o subir cambios: `git checkout develop && git pull`.
2. **Commits Semánticos:** Se utiliza el estándar *Conventional Commits* (ejemplo: `feat(tareas): agrega registro de tarea`, `fix(db): corrige mapeo de entidad`).
3. **Pull Requests obligatorios:** Cada cambio se integra a `develop` mediante un Pull Request.
4. **Revisión por pares:** Todo Pull Request debe ser revisado y aprobado por otro integrante del equipo antes del merge.
