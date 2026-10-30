# 08 — Matriz de trazabilidad técnica y observaciones

## ¿Qué significa trazabilidad?

Poder recorrer un requisito desde la acción del usuario hasta el código que lo procesa y **la tabla afectada**. Para auditoría completa de cambios de estado también harían falta fecha, actor responsable, transición anterior/nueva y evidencia persistida de cada evento.

## Matriz basada en la rama master

| Caso | Acción / regla | Endpoint | Método / repositorio | Tabla afectada | Estado |
|---|---|---|---|---|---|
| CU-02 | Cliente publica trabajo | `POST /api/trabajos` | `crearTrabajo` / `TrabajoRepository.save` | `trabajo` | Normalmente `PUBLICADO` |
| CU-04 | Profesional cotiza | `POST /api/trabajos/{id}/postulaciones` | `crearCotizacion` / `CotizacionRepository.save` | `cotizacion` | `PENDIENTE` |
| CU-05 | Cliente acepta una oferta | `POST /api/trabajos/postulaciones/{id}/aceptar` | `aceptarCotizacion` / repositorios de cotizaciones y trabajos | `trabajo`, `cotizacion` | `PUBLICADO → EN_PROCESO` |
| CU-07 | Finalización | `POST /api/trabajos/{id}/finalizar` | `finalizarTrabajo` / `TrabajoRepository.save` | `trabajo` | `EN_PROCESO → FINALIZADO` |
| CU-06 | Baja lógica | `POST /api/trabajos/{id}/baja` | `bajaLogica` / repositorios de trabajos y cotizaciones | `trabajo`, `cotizacion` | `PUBLICADO → BAJA` (flujo normal) |
| CU-08 | Enviar mensaje | `POST /api/chat` | `ChatController.enviarMensaje` / `MensajeChatRepository.save` | `mensaje_chat` | Sin cambio de estado del trabajo |
| CU-09 | Calificar | `POST /api/calificaciones` | `registrarCalificacion` / `CalificacionRepository.save` | `calificacion`; actualiza promedio en `persona` | Exige trabajo `FINALIZADO` |

### Ejemplo explicado para la defensa

**Publicar trabajo:** El cliente completa el formulario → JavaScript envía JSON → `TrabajoController.crearTrabajo` verifica datos → `TrabajoRepository.save` inserta una fila en `trabajo` → la respuesta devuelve el identificador generado. Así se puede seguir el caso de uso de punta a punta.

**Aceptar cotización:** El cliente selecciona una oferta → el controlador lee `cotizacion` → cambia el estado del trabajo a `EN_PROCESO` → asigna `profesional_asignado_id` → persiste cambios en `cotizacion` y `trabajo`.

## Hallazgos y pruebas recomendadas

| Hallazgo en master | Riesgo o pregunta para el docente | Verificación posterior |
|---|---|---|
| No hay entidad/tabla `historial_estado_trabajo` | No se identifica responsable/fecha de cada transición histórica | Revisar implementación posterior en rama local y SQL de Aiven |
| `crearTrabajo` acepta `dto.estado` | Puede eludirse estado inicial PUBLICADO | Probar alta con estado alternativo y bloquear en backend si no corresponde |
| `bajaLogica` no actualiza `activo` | Duplicidad entre bandera y enum; inconsistencias en listados | Acordar regla de baja y verificar filtros |
| `bajaLogica` no rechaza BAJA ni CANCELADO explícitamente | Reintentos o transiciones ambiguas | Probar cada estado y codificar matriz de transiciones |
| Controladores usan IDs del cuerpo/ruta | La autenticación no equivale a comprobar propiedad/rol | Probar acceso cruzado con cuentas de prueba |
| `aceptarCotizacion` realiza varias escrituras sin `@Transactional` visible | Cambios parciales en caso de fallo | Probar rollback/transacciones |
| README principal menciona `database/sistema_construir.sql`, pero el archivo real tiene otro nombre | Dificulta reconstrucción del proyecto | Corregir la ruta cuando se revise el README |
| SQL contiene columna `profesional.calificacion_promedio` ausente de la clase | Posible desalineación del esquema | Comparar DDL exportado contra base efectiva |
| No hay flujo explícito de `CANCELADO` | El enum por sí solo no define reglas de negocio | Verificar rama nueva antes de diagramar cancelación |

## Guía de evidencia por transición

Para una **trazabilidad completa** se debería poder demostrar, por cada cambio: ID de trabajo, estado anterior, estado nuevo, fecha/hora, actor responsable, operación desencadenante, filas actualizadas y registro de historial. Esta lista es un **criterio de evaluación recomendado**, no una descripción de algo ya implementado en `master`.

**Fuentes:** `controller/TrabajoController.java`, `controller/ChatController.java`, `controller/CalificacionController.java`, `models/EstadoTrabajo.java`, `models/Trabajo.java`, `database/construir_db .sql`.
