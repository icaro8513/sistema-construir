package Construir.construir.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa la postulación/oferta que hace un Profesional sobre un Trabajo.
 *
 * Esta entidad funciona como una "tabla intermedia rica":
 * relaciona Profesional + Trabajo, pero además guarda datos propios:
 *  - monto
 *  - moneda
 *  - mensaje
 *  - estado
 *  - fecha de creación
 *
 * NUEVO:
 *  - moneda:
 *      permite indicar si la cotización está en pesos argentinos o dólares.
 */
@Entity
@Table(
        name = "cotizacion",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cotizacion_trabajo_profesional",
                        columnNames = {"trabajo_id", "profesional_id"}
                )
        }
        // Evita que un mismo profesional oferte más de una vez al mismo trabajo.
)
public class Cotizacion {

    // =========================================================
    // IDENTIFICADOR
    // =========================================================

    /**
     * Identificador único de la cotización.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================================================
    // TRABAJO ASOCIADO
    // =========================================================

    /**
     * Trabajo al que pertenece esta oferta.
     * Un trabajo puede tener muchas cotizaciones.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "trabajo_id", nullable = false)
    private Trabajo trabajo;

    // =========================================================
    // PROFESIONAL QUE COTIZA
    // =========================================================

    /**
     * Profesional que realiza la cotización.
     * Un profesional puede cotizar muchos trabajos.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "profesional_id", nullable = false)
    private Profesional profesional;

    // =========================================================
    // MONTO Y MONEDA
    // =========================================================

    /**
     * Monto ofertado por el profesional.
     * Se usa BigDecimal porque es lo correcto para manejar dinero.
     */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    /**
     * Moneda de la cotización.
     *
     * Valores posibles:
     *  - ARS: pesos argentinos
     *  - USD: dólares estadounidenses
     *
     * Importante:
     * No la marcamos como nullable=false todavía para no romper cotizaciones viejas
     * que fueron creadas antes de agregar este campo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "moneda", length = 10)
    private MonedaCotizacion moneda;

    // =========================================================
    // MENSAJE
    // =========================================================

    /**
     * Mensaje opcional de la oferta.
     * Ejemplo:
     * "Incluye mano de obra. Materiales aparte."
     */
    @Column(length = 500)
    private String mensaje;

    // =========================================================
    // ESTADO
    // =========================================================

    /**
     * Estado de la cotización.
     * Puede ser:
     *  - PENDIENTE
     *  - ACEPTADA
     *  - RECHAZADA
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCotizacion estado;

    // =========================================================
    // FECHA DE CREACIÓN
    // =========================================================

    /**
     * Fecha y hora en que se creó la cotización.
     */
    @Column(name = "creada_en", nullable = false)
    private LocalDateTime creadaEn;

    // =========================================================
    // VALORES POR DEFECTO
    // =========================================================

    /**
     * Hook de JPA.
     * Se ejecuta automáticamente antes de insertar la cotización en la base.
     */
    @PrePersist
    protected void onCreate() {
        if (this.creadaEn == null) {
            this.creadaEn = LocalDateTime.now();
        }

        if (this.estado == null) {
            this.estado = EstadoCotizacion.PENDIENTE;
        }

        // Si por algún motivo no llega moneda desde el frontend,
        // dejamos ARS por defecto.
        if (this.moneda == null) {
            this.moneda = MonedaCotizacion.ARS;
        }
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MonedaCotizacion getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedaCotizacion moneda) {
        this.moneda = moneda;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public EstadoCotizacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoCotizacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreadaEn() {
        return creadaEn;
    }

    public void setCreadaEn(LocalDateTime creadaEn) {
        this.creadaEn = creadaEn;
    }
}