package Construir.construir.dtos;

/**
 * DTO de entrada para crear un Trabajo.
 *
 * Este DTO recibe los datos que manda el frontend cuando un cliente publica un trabajo.
 *
 * Importante:
 * No usamos directamente la entidad Trabajo en el request porque es mejor recibir
 * datos simples y después convertirlos en el controlador.
 *
 * NUEVO:
 *  - especialidadRequerida:
 *      permite que el cliente indique qué tipo de profesional necesita.
 *      Es opcional. Si viene vacío o null, significa:
 *      "No estoy seguro / cualquier profesional".
 *
 * Valores esperados según el enum Especialidad:
 *  - ELECTRICISTA
 *  - GASISTA
 *  - ALBAÑIL
 *  - PLOMERO
 *  - PINTOR
 *  - MAESTRO_MAYOR_OBRA
 *  - OTRO
 */
public class TrabajoDTO {

    // ID del cliente que publica el trabajo.
    public Long clienteId;

    // Título corto del trabajo.
    public String titulo;

    // Descripción detallada del trabajo.
    public String descripcion;

    // Medidas o referencias técnicas.
    public String medidas;

    // Ubicación del trabajo.
    public String ubicacion;

    // NUEVO: tipo de profesional requerido por el cliente.
    // Puede venir vacío si el cliente no está seguro.
    public String especialidadRequerida;

    // Estado inicial del trabajo.
    // Normalmente será PUBLICADO.
    public String estado;
}