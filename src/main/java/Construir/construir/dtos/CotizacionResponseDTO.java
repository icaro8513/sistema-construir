package Construir.construir.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de SALIDA para devolver datos planos de una cotización sin ciclos JSON.
 *
 * Se usa cuando el frontend necesita mostrar las cotizaciones de un trabajo.
 *
 * NUEVO:
 *  - moneda:
 *      permite mostrar si el monto fue cotizado en ARS o USD.
 */
public class CotizacionResponseDTO {

    // Identificador de la cotización
    private Long id;

    // ID del trabajo asociado
    private Long trabajoId;

    // ID del profesional que cotizó
    private Long profesionalId;

    // Monto ofertado
    private BigDecimal monto;

    // Moneda de la cotización: ARS o USD
    private String moneda;

    // Mensaje opcional del profesional
    private String mensaje;

    // Estado: PENDIENTE | ACEPTADA | RECHAZADA
    private String estado;

    // Fecha/hora de creación de la cotización
    private LocalDateTime creadaEn;

    // Constructor vacío necesario para serialización/deserialización
    public CotizacionResponseDTO() {
    }

    /**
     * Constructor nuevo con moneda.
     */
    public CotizacionResponseDTO(Long id,
                                 Long trabajoId,
                                 Long profesionalId,
                                 BigDecimal monto,
                                 String moneda,
                                 String mensaje,
                                 String estado,
                                 LocalDateTime creadaEn) {
        this.id = id;
        this.trabajoId = trabajoId;
        this.profesionalId = profesionalId;
        this.monto = monto;
        this.moneda = moneda;
        this.mensaje = mensaje;
        this.estado = estado;
        this.creadaEn = creadaEn;
    }

    /**
     * Constructor anterior sin moneda.
     * Lo dejamos para no romper el proyecto mientras actualizamos TrabajoController.
     */
    public CotizacionResponseDTO(Long id,
                                 Long trabajoId,
                                 Long profesionalId,
                                 BigDecimal monto,
                                 String mensaje,
                                 String estado,
                                 LocalDateTime creadaEn) {
        this.id = id;
        this.trabajoId = trabajoId;
        this.profesionalId = profesionalId;
        this.monto = monto;
        this.moneda = "ARS";
        this.mensaje = mensaje;
        this.estado = estado;
        this.creadaEn = creadaEn;
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrabajoId() {
        return trabajoId;
    }

    public void setTrabajoId(Long trabajoId) {
        this.trabajoId = trabajoId;
    }

    public Long getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(Long profesionalId) {
        this.profesionalId = profesionalId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreadaEn() {
        return creadaEn;
    }

    public void setCreadaEn(LocalDateTime creadaEn) {
        this.creadaEn = creadaEn;
    }
}