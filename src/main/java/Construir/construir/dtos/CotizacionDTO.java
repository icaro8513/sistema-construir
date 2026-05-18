package Construir.construir.dtos;

import java.math.BigDecimal;

/**
 * DTO de ENTRADA para crear una cotización/postulación sobre un trabajo.
 *
 * Este DTO recibe los datos que manda el frontend cuando un profesional
 * se postula a un trabajo publicado por un cliente.
 *
 * Se usa junto al path variable {id} del trabajo:
 *
 * POST /api/trabajos/{id}/postulaciones
 *
 * NUEVO:
 *  - moneda:
 *      permite indicar si el monto está en pesos argentinos o dólares.
 *
 * Valores esperados:
 *  - ARS
 *  - USD
 */
public class CotizacionDTO {

    /**
     * ID del profesional que realiza la oferta.
     */
    public Long profesionalId;

    /**
     * Monto ofertado.
     *
     * Se usa BigDecimal porque es lo correcto para manejar dinero.
     */
    public BigDecimal monto;

    /**
     * Moneda de la cotización.
     *
     * Valores válidos:
     *  - ARS: pesos argentinos
     *  - USD: dólares estadounidenses
     *
     * Si viene vacío o null, después en el controlador vamos a dejar ARS por defecto.
     */
    public String moneda;

    /**
     * Mensaje opcional de la cotización.
     *
     * Ejemplo:
     * "Incluye mano de obra. Materiales aparte."
     */
    public String mensaje;
}