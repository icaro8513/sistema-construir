# 04 — Máquina de estados de Trabajo

Se documentan **transiciones verificadas en `TrabajoController` de la rama `master`**.

~~~mermaid
stateDiagram-v2
    [*] --> PUBLICADO: alta habitual
    PUBLICADO --> EN_PROCESO: aceptar cotizacion
    EN_PROCESO --> FINALIZADO: finalizar
    PUBLICADO --> BAJA: baja logica

    note right of PUBLICADO
      Admite cotizaciones.
      El alta actual permite recibir dto.estado.
    end note

    note right of EN_PROCESO
      Exige profesional asignado
      para finalizar.
    end note
~~~

## Transiciones implementadas

| Estado de origen | Acción | Estado destino | Verificación y persistencia |
|---|---|---|---|
| Alta habitual | Crear trabajo | PUBLICADO | `crearTrabajo` → `TrabajoRepository.save` |
| PUBLICADO | Aceptar cotización | EN_PROCESO | Asigna profesional, acepta una cotización, rechaza las restantes y guarda trabajo |
| EN_PROCESO | Finalizar | FINALIZADO | Exige profesional asignado; guarda trabajo |
| PUBLICADO | Dar de baja | BAJA | Rechaza cotizaciones y guarda trabajo |

## Hallazgos importantes

- El enum contiene cinco valores: `PUBLICADO`, `EN_PROCESO`, `FINALIZADO`, `BAJA`, `CANCELADO`. **No existe un endpoint específico de cancelación** en el controlador de `master`.
- El alta acepta opcionalmente `dto.estado` y puede asignar cualquiera de los valores válidos del enum: por eso el diagrama anterior refleja el **flujo habitual**, no una garantía rígida sobre el estado inicial.
- El endpoint de baja bloquea `EN_PROCESO` y `FINALIZADO`, pero **no bloquea expresamente `BAJA` ni `CANCELADO`**; podría realizar `BAJA → BAJA` o `CANCELADO → BAJA`.
- El método `bajaLogica` cambia `estado = BAJA` pero **no cambia el booleano `activo` a false**. Debe decidirse cuál de los dos representa formalmente la baja.
- No se observó `historial_estado_trabajo` en las entidades ni en el SQL de `master`. **No se puede afirmar que cada cambio deje un registro de auditoría en esta versión.**
- No se observó llamada a `delete` al dar de baja: es una **baja por cambio de estado**, no borrado físico.

### Diferencia con la rama en validación

Los cambios posteriores de `deploy-refactor` podrían haber corregido estas inconsistencias. Esta documentación deberá regenerarse o actualizarse cuando la versión final se publique.

**Fuentes:** `models/EstadoTrabajo.java`, `models/Trabajo.java`, `controller/TrabajoController.java`.
