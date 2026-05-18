package Construir.construir.models;

/**
 * Estados del ciclo de vida de una cotización/postulación.
 */
public enum EstadoCotizacion {
    PENDIENTE,   // enviada y no decidida por el cliente
    ACEPTADA,    // el cliente la acepto (se va a asignar el profesional al trabajo)
    RECHAZADA    // el cliente la rechazo o se rechazo automaticamente al aceptar otra
}
