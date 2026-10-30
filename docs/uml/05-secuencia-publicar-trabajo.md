# 05 — Secuencia: publicar trabajo

Caso de uso CU-02, tal como aparece en `master`.

~~~mermaid
sequenceDiagram
    autonumber
    actor Cliente
    participant UI as cliente-trabajo.html
    participant Seguridad as JwtAuthenticationFilter
    participant Controlador as TrabajoController
    participant Personas as PersonaRepository
    participant Trabajos as TrabajoRepository
    participant BD as Base de datos

    Cliente->>UI: Completa titulo, descripcion, medidas, ubicacion
    UI->>Seguridad: POST /api/trabajos (JWT, TrabajoDTO)
    Seguridad->>Controlador: Peticion autenticada
    Controlador->>Controlador: Valida clienteId y titulo
    Controlador->>Personas: findById(clienteId)
    Personas->>BD: SELECT persona / cliente
    BD-->>Personas: Persona
    Personas-->>Controlador: Optional Persona
    alt No existe o no es Cliente
        Controlador-->>UI: 400 Bad Request
    else Es Cliente
        Controlador->>Controlador: Crea Trabajo y define estado
        Controlador->>Trabajos: save(trabajo)
        Trabajos->>BD: INSERT trabajo
        BD-->>Trabajos: ID generado
        Trabajos-->>Controlador: Trabajo persistido
        Controlador-->>UI: 200 + TrabajoResponseDTO
        UI-->>Cliente: Muestra resultado
    end
~~~

## Trazabilidad de campos

| Dato de interfaz/DTO | Atributo del modelo | Columna SQL |
|---|---|---|
| clienteId | Trabajo.cliente | trabajo.cliente_id |
| titulo | Trabajo.titulo | trabajo.titulo |
| descripcion | Trabajo.descripcion | trabajo.descripcion |
| medidas | Trabajo.medidas | trabajo.medidas |
| ubicacion | Trabajo.ubicacion | trabajo.ubicacion |
| especialidadRequerida | Trabajo.especialidadRequerida | trabajo.especialidad_requerida |
| estado (opcional) | Trabajo.estado | trabajo.estado |

**Regla detectada:** el frontend usa `fetch('/api/trabajos', ...)` desde `cliente-trabajo.html`. El backend valida que `clienteId` exista y sea del tipo `Cliente`. **Pendiente de verificar/corregir:** vincular ese `clienteId` al usuario autenticado en vez de confiar en el identificador enviado en la solicitud.

**Fuentes:** `static/cliente/cliente-trabajo.html`, `dtos/TrabajoDTO.java`, `controller/TrabajoController.java`, `repositories/PersonaRepository.java`, `repositories/TrabajoRepository.java`.
