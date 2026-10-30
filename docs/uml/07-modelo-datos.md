# 07 — Diagrama entidad-relación (DER)

Este DER representa las **tablas y claves foráneas del script** `database/construir_db .sql` presente en `master`. Se muestran columnas seleccionadas para mantener el dibujo legible.

~~~mermaid
erDiagram
    persona {
        bigint id PK
        varchar nombre
        varchar apellido
        varchar email
        varchar tipo_persona
    }
    cliente {
        bigint id PK,FK
        varchar direccion
    }
    profesional {
        bigint id PK,FK
        varchar especialidad
        varchar experiencia
        varchar direccion
    }
    trabajo {
        bigint id PK
        bigint cliente_id FK
        bigint profesional_asignado_id FK
        varchar titulo
        varchar descripcion
        varchar estado
        boolean activo
    }
    cotizacion {
        bigint id PK
        bigint trabajo_id FK
        bigint profesional_id FK
        decimal monto
        varchar moneda
        varchar estado
    }
    mensaje_chat {
        bigint id PK
        bigint trabajo_id FK
        bigint remitente_id FK
        varchar contenido
    }
    calificacion {
        bigint id PK
        bigint trabajo_id FK
        bigint autor_id FK
        bigint destinatario_id FK
        int puntaje
    }

    persona ||--o| cliente : "subtipo"
    persona ||--o| profesional : "subtipo"
    cliente ||--o{ trabajo : "publica"
    profesional o|--o{ trabajo : "asignado"
    trabajo ||--o{ cotizacion : "recibe"
    profesional ||--o{ cotizacion : "presenta"
    trabajo ||--o{ mensaje_chat : "contiene"
    persona ||--o{ mensaje_chat : "remite"
    trabajo ||--o{ calificacion : "tiene"
    persona ||--o{ calificacion : "emite"
    persona ||--o{ calificacion : "recibe"
~~~

## Reglas de integridad

- `cliente.id` y `profesional.id` son claves foráneas a `persona.id` (herencia JPA `JOINED`).
- `trabajo.cliente_id` es obligatorio; `profesional_asignado_id` es opcional.
- `cotizacion` referencia un trabajo y un profesional; el modelo JPA declara restricción única (trabajo, profesional).
- `calificacion` referencia autor, destinatario y trabajo; el modelo JPA declara restricción única (autor, destinatario, trabajo).
- `mensaje_chat` referencia remitente y trabajo.

## Precisión sobre versiones

**No aparece `historial_estado_trabajo` en el script de `master`**. En consecuencia, **la trazabilidad temporal de cambios de estado no puede explicarse como una tabla existente en este DER**.

Se detecta también una discrepancia histórica: el SQL exportado contiene `profesional.calificacion_promedio`, mientras que la clase `Profesional` de esta instantánea ya no define ese atributo. Es una señal de que el script puede no coincidir exactamente con el esquema generado por la aplicación.

**Nota:** Algunas columnas del script se omiten en el dibujo, pero no se afirma que las tablas tengan solo esos campos. Confirmar tipos, restricciones e índices contra la base efectiva antes de presentar un DER físico definitivo.

**Fuentes:** `database/construir_db .sql` y `models/`.
