package Construir.construir.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa un trabajo publicado por un Cliente.
 *
 * Flujo:
 *  - Cliente publica el trabajo con estado PUBLICADO.
 *  - Profesionales se postulan mediante Cotizacion.
 *  - Cliente acepta una cotización.
 *  - Se asigna un profesional y el trabajo pasa a EN_PROCESO.
 *  - Al finalizar, el trabajo pasa a FINALIZADO.
 *  - Recién ahí se habilitan las calificaciones.
 *
 * NUEVO:
 *  - especialidadRequerida:
 *      permite indicar qué tipo de profesional necesita el cliente.
 *      Es opcional porque el cliente puede no estar seguro.
 */
@Entity
@Table(name = "trabajo")
public class Trabajo {

    // =========================================================
    // IDENTIFICADOR
    // =========================================================

    /**
     * Identificador único del trabajo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================================================
    // CLIENTE DUEÑO DEL TRABAJO
    // =========================================================

    /**
     * Cliente que publica el trabajo.
     * Todo trabajo debe pertenecer a un cliente.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // =========================================================
    // DATOS DESCRIPTIVOS DEL TRABAJO
    // =========================================================

    /**
     * Título corto del trabajo.
     * Ejemplo: "Cambio de tablero eléctrico".
     */
    private String titulo;

    /**
     * Descripción detallada del trabajo.
     */
    @Column(length = 1000)
    private String descripcion;

    /**
     * Medidas aproximadas o datos técnicos del trabajo.
     * Ejemplo: "10 mts de altura", "3x4 m".
     */
    private String medidas;

    /**
     * Ubicación o referencia del trabajo.
     */
    private String ubicacion;

    /**
     * Tipo de profesional que el cliente considera necesario.
     *
     * Es opcional:
     *  - null significa que el cliente no está seguro o acepta cualquier rubro.
     *
     * Usa el enum Especialidad existente:
     *  - ELECTRICISTA
     *  - GASISTA
     *  - ALBAÑIL
     *  - PLOMERO
     *  - PINTOR
     *  - MAESTRO_MAYOR_OBRA
     *  - OTRO
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "especialidad_requerida", length = 50)
    private Especialidad especialidadRequerida;

    // =========================================================
    // ESTADO DEL TRABAJO
    // =========================================================

    /**
     * Estado actual del trabajo.
     * Por defecto, al crearse queda PUBLICADO.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTrabajo estado = EstadoTrabajo.PUBLICADO;

    // =========================================================
    // PROFESIONAL ASIGNADO
    // =========================================================

    /**
     * Profesional asignado al trabajo.
     * Se carga cuando el cliente acepta una cotización.
     */
    @ManyToOne
    @JoinColumn(name = "profesional_asignado_id")
    private Profesional profesionalAsignado;

    // =========================================================
    // COTIZACIONES
    // =========================================================

    /**
     * Cotizaciones asociadas a este trabajo.
     *
     * mappedBy = "trabajo":
     *  - indica que la relación la maneja la entidad Cotizacion.
     *
     * JsonIgnore:
     *  - evita problemas de serialización circular al devolver datos en JSON.
     */
    @OneToMany(mappedBy = "trabajo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Cotizacion> cotizaciones = new ArrayList<>();

    // =========================================================
    // BAJA LÓGICA
    // =========================================================

    /**
     * Campo para baja lógica.
     * Actualmente también se usa EstadoTrabajo.BAJA.
     * Se mantiene porque ya existe en tu sistema.
     */
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean activo = true;

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Especialidad getEspecialidadRequerida() {
        return especialidadRequerida;
    }

    public void setEspecialidadRequerida(Especialidad especialidadRequerida) {
        this.especialidadRequerida = especialidadRequerida;
    }

    public EstadoTrabajo getEstado() {
        return estado;
    }

    public void setEstado(EstadoTrabajo estado) {
        this.estado = estado;
    }

    public Profesional getProfesionalAsignado() {
        return profesionalAsignado;
    }

    public void setProfesionalAsignado(Profesional profesionalAsignado) {
        this.profesionalAsignado = profesionalAsignado;
    }

    public List<Cotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // =========================================================
    // HELPERS
    // =========================================================

    /**
     * Agrega una cotización a este trabajo y mantiene la relación bidireccional.
     */
    public void addCotizacion(Cotizacion c) {
        if (c == null) return;
        cotizaciones.add(c);
        c.setTrabajo(this);
    }

    /**
     * Elimina una cotización de este trabajo y mantiene la relación bidireccional.
     */
    public void removeCotizacion(Cotizacion c) {
        if (c == null) return;
        cotizaciones.remove(c);

        if (c.getTrabajo() == this) {
            c.setTrabajo(null);
        }
    }
}