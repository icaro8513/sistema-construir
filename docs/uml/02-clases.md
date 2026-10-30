# 02 — Diagramas de clases UML

Los nombres se toman de las entidades JPA en `src/main/java/Construir/construir/models/`.

## Personas, trabajos y cotizaciones

~~~mermaid
classDiagram
    direction LR
    class Persona {
      <<abstract>>
      +Long id
      +String nombre
      +String apellido
      +String email
      +String telefono
      +TipoPersona tipoPersona
      +Double puntajePromedio
    }
    class Cliente {
      +String direccion
    }
    class Profesional {
      +Especialidad especialidad
      +String experiencia
      +String direccion
    }
    class Trabajo {
      +Long id
      +String titulo
      +String descripcion
      +String medidas
      +String ubicacion
      +Especialidad especialidadRequerida
      +EstadoTrabajo estado
      +boolean activo
      +addCotizacion(Cotizacion)
      +removeCotizacion(Cotizacion)
    }
    class Cotizacion {
      +Long id
      +BigDecimal monto
      +MonedaCotizacion moneda
      +String mensaje
      +EstadoCotizacion estado
      +LocalDateTime creadaEn
    }

    Persona <|-- Cliente
    Persona <|-- Profesional
    Cliente "1" --> "0..*" Trabajo : publica
    Profesional "0..1" <-- "0..*" Trabajo : asignado a
    Trabajo "1" --> "0..*" Cotizacion : recibe
    Profesional "1" --> "0..*" Cotizacion : presenta
~~~

**Lectura:** Un cliente publica muchos trabajos; cada trabajo pertenece a un cliente. Un profesional puede cotizar múltiples trabajos y recibir asignaciones. `Persona` utiliza herencia JPA `JOINED`.

## Chat y reputación

~~~mermaid
classDiagram
    direction LR
    class Persona {
      +Long id
      +String nombre
    }
    class Trabajo {
      +Long id
      +EstadoTrabajo estado
    }
    class MensajeChat {
      +Long id
      +String contenido
      +String remitenteRol
      +LocalDateTime enviadoEn
    }
    class Calificacion {
      +Long id
      +int puntaje
      +String comentario
      +LocalDate fecha
    }

    Trabajo "1" --> "0..*" MensajeChat : contiene
    Persona "1" --> "0..*" MensajeChat : remite
    Trabajo "1" --> "0..*" Calificacion : asociada a
    Persona "1" --> "0..*" Calificacion : autor
    Persona "1" --> "0..*" Calificacion : destinatario
~~~

**Reglas relevantes:** `Cotizacion` impone unicidad del par trabajo-profesional. `Calificacion` impone unicidad de autor-destinatario-trabajo y puntaje de 1 a 5.

### Fuente y limitaciones

- `models/Persona.java`, `Cliente.java`, `Profesional.java`, `Trabajo.java`, `Cotizacion.java`, `Calificacion.java`, `MensajeChat.java`.
- Se omitieron getters/setters rutinarios para mejorar legibilidad y **no se exhibe el campo de contraseña** en el diagrama.
- Las asociaciones muestran el modelo conceptual. El DER especifica las claves foráneas del SQL.
