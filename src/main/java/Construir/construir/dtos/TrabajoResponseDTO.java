package Construir.construir.dtos;

/**
 * DTO de salida para Trabajo.
 *
 * Este DTO se usa para enviar datos del backend al frontend.
 *
 * ¿Por qué usamos un DTO?
 *  - Evita devolver directamente la entidad Trabajo.
 *  - Evita errores por relaciones entre entidades.
 *  - Permite controlar exactamente qué datos ve el frontend.
 *
 * NUEVO:
 *  - especialidadRequerida:
 *      permite mostrar en pantalla qué tipo de profesional pidió el cliente.
 */
public class TrabajoResponseDTO {

    // =========================================================
    // DATOS PRINCIPALES DEL TRABAJO
    // =========================================================

    private Long id;
    private String titulo;
    private String descripcion;
    private String medidas;
    private String ubicacion;

    // NUEVO: tipo de profesional requerido por el cliente
    private String especialidadRequerida;

    private String estado;

    // =========================================================
    // DATOS DEL CLIENTE
    // =========================================================

    private Long clienteId;
    private String clienteNombre;

    // =========================================================
    // DATOS DEL PROFESIONAL ASIGNADO
    // =========================================================

    private Long profesionalAsignadoId;
    private String profesionalAsignadoNombre;

    // =========================================================
    // CONSTRUCTOR VACÍO
    // =========================================================

    public TrabajoResponseDTO() {
    }

    // =========================================================
    // CONSTRUCTOR NUEVO CON especialidadRequerida
    // =========================================================

    public TrabajoResponseDTO(Long id,
                              String titulo,
                              String descripcion,
                              String medidas,
                              String ubicacion,
                              String especialidadRequerida,
                              String estado,
                              Long clienteId,
                              String clienteNombre,
                              Long profesionalAsignadoId,
                              String profesionalAsignadoNombre) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.medidas = medidas;
        this.ubicacion = ubicacion;
        this.especialidadRequerida = especialidadRequerida;
        this.estado = estado;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.profesionalAsignadoId = profesionalAsignadoId;
        this.profesionalAsignadoNombre = profesionalAsignadoNombre;
    }

    // =========================================================
    // CONSTRUCTOR ANTERIOR
    // =========================================================
    // Lo dejamos para que el proyecto no se rompa mientras todavía
    // no modificamos TrabajoController.
    // Más adelante el controlador usará el constructor nuevo.

    public TrabajoResponseDTO(Long id,
                              String titulo,
                              String descripcion,
                              String medidas,
                              String ubicacion,
                              String estado,
                              Long clienteId,
                              String clienteNombre,
                              Long profesionalAsignadoId,
                              String profesionalAsignadoNombre) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.medidas = medidas;
        this.ubicacion = ubicacion;
        this.especialidadRequerida = null;
        this.estado = estado;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.profesionalAsignadoId = profesionalAsignadoId;
        this.profesionalAsignadoNombre = profesionalAsignadoNombre;
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

    public String getEspecialidadRequerida() {
        return especialidadRequerida;
    }

    public void setEspecialidadRequerida(String especialidadRequerida) {
        this.especialidadRequerida = especialidadRequerida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getProfesionalAsignadoId() {
        return profesionalAsignadoId;
    }

    public void setProfesionalAsignadoId(Long profesionalAsignadoId) {
        this.profesionalAsignadoId = profesionalAsignadoId;
    }

    public String getProfesionalAsignadoNombre() {
        return profesionalAsignadoNombre;
    }

    public void setProfesionalAsignadoNombre(String profesionalAsignadoNombre) {
        this.profesionalAsignadoNombre = profesionalAsignadoNombre;
    }
}