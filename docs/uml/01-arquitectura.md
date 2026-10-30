# 01 — Arquitectura de Construx.com

## Vista de componentes

~~~mermaid
flowchart LR
    U["Cliente / Profesional"] --> UI["HTML, CSS y JavaScript"]
    UI -->|"HTTP fetch + JWT"| SEC["Spring Security / JwtAuthenticationFilter"]
    SEC --> CTRL["Controladores REST"]
    CTRL --> JPA["Repositorios Spring Data JPA"]
    JPA --> DB[("Base de datos relacional MySQL/MariaDB")]
    CTRL --> DTO["DTOs de entrada y respuesta"]
    AUTH["AuthController"] --> JWT["JwtUtil"]
    AUTH --> JPA
    JWT -. "JWT para login" .-> UI
~~~

## Capas verificadas

| Capa | Implementación observada | Evidencia |
|---|---|---|
| Presentación | Páginas HTML/JS estáticas, peticiones fetch | `src/main/resources/static/` |
| Acceso | Autenticación JWT, Spring Security sin sesión HTTP | `config/SecurityConfig.java`, `security/JwtAuthenticationFilter.java` |
| API | `AuthController`, `TrabajoController`, `ChatController`, `CalificacionController` | `controller/` |
| Persistencia | Repositorios JPA que heredan de `JpaRepository` | `repositories/` |
| Modelo | Entidades JPA y enums | `models/` |
| Datos | Script SQL con tablas y claves foráneas | `database/construir_db .sql` |

**Tecnologías verificadas en build.gradle:** Java 17, Spring Boot 3.5.6, Spring Web, Spring Data JPA, Spring Security, validación, conector MySQL, JWT y Gradle.

## Precauciones

1. En `master`, el controlador de trabajos accede directamente a los repositorios; **no** hay una capa `services/` para ese módulo. No se inventa en el dibujo.
2. El despliegue actual (Render/Aiven) y la rama local en validación **deben comprobarse en su versión correspondiente**: no se deducen de este snapshot.
3. `SecurityConfig` exige autenticación para `/api/trabajos/**`, pero eso **no acredita por sí solo autorización por dueño y rol** en cada operación.
