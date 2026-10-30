# Construx.com — Documentación UML y trazabilidad

> **Alcance:** instantánea del código existente en la rama `master` de GitHub al 29/10/2026 (commit `3d3e0d108a7bdf73926d467608d59d368111fcb9`). **No representa automáticamente los cambios de `deploy-refactor` ni el despliegue actual.** Documentación académica inicial, pendiente de revisión tras las validaciones.

## Índice

1. [Arquitectura](01-arquitectura.md)
2. [Clases UML](02-clases.md)
3. [Casos de uso](03-casos-de-uso.md)
4. [Estados del trabajo](04-estados-trabajo.md)
5. [Secuencia: publicar trabajo](05-secuencia-publicar-trabajo.md)
6. [Secuencia: cotizaciones](06-secuencia-cotizaciones.md)
7. [Modelo de datos (DER)](07-modelo-datos.md)
8. [Trazabilidad y hallazgos](08-trazabilidad.md)

Los gráficos se generan con [Mermaid](https://mermaid.js.org/) y se renderizan directamente al abrir cada archivo Markdown en GitHub.

### Reglas de lectura

- **Confirmado:** se observa en clases, endpoints, código de interfaz o archivo SQL de `master`.
- **Pendiente de verificar:** cambios posteriores, reglas del sistema desplegado o mecanismos no encontrados en esta instantánea.
- Se representan **las transiciones que ejecuta el controlador**, no las que solo figuran en comentarios.
- El archivo SQL disponible es `database/construir_db .sql` (incluye un espacio antes de `.sql`).
- La tabla `historial_estado_trabajo` **no figura en los archivos examinados de `master`**. No se incluye en el DER confirmado.

### Fuentes principales

- `src/main/java/Construir/construir/models/`
- `src/main/java/Construir/construir/controller/`
- `src/main/java/Construir/construir/repositories/`
- `src/main/java/Construir/construir/config/SecurityConfig.java`
- `src/main/resources/static/cliente/cliente-trabajo.html`
- `database/construir_db .sql`

**Nota:** Los diagramas no sustituyen una revisión del sistema en ejecución ni pruebas automatizadas. No se realizaron cambios de código, base de datos ni despliegue para elaborar estos documentos.
