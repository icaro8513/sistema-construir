# 03 — Casos de uso por actor

Esta instantánea tiene roles `CLIENTE` y `PROFESIONAL` en `TipoPersona`. **No se identificó aquí un rol administrador en el enum.** Se utiliza un `flowchart` Mermaid como **mapa funcional aproximado**: no es un diagrama de casos de uso UML de notación estricta.

~~~mermaid
flowchart LR
    C["Actor: Cliente"]
    P["Actor: Profesional"]
    A["Autenticarse"]
    PUB["Publicar trabajo"]
    LIST["Consultar trabajos"]
    COT["Enviar cotización"]
    ACEP["Aceptar cotización"]
    BAJA["Dar de baja trabajo"]
    FIN["Finalizar trabajo"]
    CHAT["Intercambiar mensajes"]
    CAL["Calificar"]

    C --> A
    P --> A
    C --> PUB
    C --> LIST
    P --> LIST
    P --> COT
    C --> ACEP
    C --> BAJA
    C --> FIN
    C --> CHAT
    P --> CHAT
    C --> CAL
    P --> CAL
~~~

## Catálogo resumido

| ID | Caso de uso | Actor de negocio | Endpoint observado |
|---|---|---|---|
| CU-01 | Registrarse e iniciar sesión | Cliente / Profesional | `POST /api/auth/registro/cliente`, `/profesional`, `/login` |
| CU-02 | Publicar trabajo | Cliente | `POST /api/trabajos` |
| CU-03 | Consultar trabajos | Cliente / Profesional | `GET /api/trabajos`, `GET /api/trabajos/{id}` |
| CU-04 | Cotizar un trabajo | Profesional | `POST /api/trabajos/{id}/postulaciones` |
| CU-05 | Aceptar cotización | Cliente | `POST /api/trabajos/postulaciones/{cotizacionId}/aceptar` |
| CU-06 | Dar de baja un trabajo | Cliente | `POST /api/trabajos/{trabajoId}/baja` |
| CU-07 | Finalizar trabajo | Cliente / flujo del trabajo | `POST /api/trabajos/{trabajoId}/finalizar` |
| CU-08 | Enviar y consultar mensajes | Cliente / Profesional | `POST /api/chat`, `GET /api/chat/trabajo/{trabajoId}` |
| CU-09 | Calificar después de finalizar | Cliente / Profesional | `POST /api/calificaciones` |

### Importante para la defensa

La columna **actor de negocio** refleja quién debería ejecutar la operación según el flujo. **No demuestra que todos esos permisos estén validados por el backend**: hace falta probar autorización y pertenencia del usuario autenticado. En `master`, el controlador usa identificadores provistos por la petición para varias operaciones.

**Fuentes:** `models/TipoPersona.java`, `controller/TrabajoController.java`, `controller/AuthController.java`, `controller/ChatController.java`, `controller/CalificacionController.java`.
