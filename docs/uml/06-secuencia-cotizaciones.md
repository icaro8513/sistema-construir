# 06 — Cotizaciones y asignación de un profesional

## Aceptar una cotización

~~~mermaid
sequenceDiagram
    autonumber
    actor Cliente
    participant UI as Frontend
    participant API as TrabajoController
    participant CR as CotizacionRepository
    participant TR as TrabajoRepository
    participant BD as Base de datos

    Cliente->>UI: Selecciona cotizacion
    UI->>API: POST /api/trabajos/postulaciones/{id}/aceptar
    API->>CR: findById(cotizacionId)
    CR->>BD: SELECT cotizacion
    BD-->>CR: Cotizacion
    CR-->>API: Cotizacion con Trabajo
    alt Trabajo no esta PUBLICADO
        API-->>UI: 400 Bad Request
    else Trabajo PUBLICADO
        API->>API: Asignar profesional al Trabajo
        API->>API: Trabajo.estado = EN_PROCESO
        API->>CR: save(cotizacion ACEPTADA)
        API->>CR: findByTrabajo_Id(trabajoId)
        CR->>BD: SELECT cotizaciones del trabajo
        BD-->>CR: Lista de cotizaciones
        loop Cada otra cotizacion
            API->>CR: save(cotizacion RECHAZADA)
        end
        API->>TR: save(trabajo EN_PROCESO)
        TR->>BD: UPDATE trabajo
        API-->>UI: 200 + CotizacionResponseDTO
    end
~~~

## Cotizar un trabajo

`POST /api/trabajos/{id}/postulaciones` solo admite trabajos `PUBLICADO`. Valida que el emisor exista y sea un `Profesional`, que el monto esté presente y que ese profesional no haya cotizado antes el mismo trabajo. La entidad `Cotizacion` guarda `PENDIENTE` inicialmente y utiliza `ARS` por defecto cuando no se informa moneda; también admite `USD`.

## Riesgos y reglas de negocio

- La aceptación actual actualiza **cotización elegida, cotizaciones restantes y trabajo** mediante varias llamadas a `save`. No se observó `@Transactional` en ese método del controlador. Se debe verificar la atomicidad y agregar una transacción si corresponde.
- La aceptación comprueba estado `PUBLICADO`, pero no se observó en este método una validación de que quien ejecuta la petición sea el cliente dueño.
- No se representa historial de estados porque no existe en esta instantánea.

**Fuentes:** `controller/TrabajoController.java`, `models/Cotizacion.java`, `models/Trabajo.java`, `repositories/CotizacionRepository.java`, `repositories/TrabajoRepository.java`.
