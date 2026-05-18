package Construir.construir.dtos;

import java.time.LocalDate;

/**
 * DTO para responder con información resumida de una calificación.
 */
public class CalificacionResponseDTO {

    private Long id;
    private Long autorId;
    private String autorNombre;
    private Long destinatarioId;
    private String destinatarioNombre;
    private Long trabajoId;
    private int puntaje;
    private String comentario;
    private LocalDate fecha;

    // === Constructores ===

    public CalificacionResponseDTO() {}

    public CalificacionResponseDTO(Long id, Long autorId, String autorNombre,
                                   Long destinatarioId, String destinatarioNombre,
                                   Long trabajoId, int puntaje,
                                   String comentario, LocalDate fecha) {
        this.id = id;
        this.autorId = autorId;
        this.autorNombre = autorNombre;
        this.destinatarioId = destinatarioId;
        this.destinatarioNombre = destinatarioNombre;
        this.trabajoId = trabajoId;
        this.puntaje = puntaje;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    // === Getters y Setters ===

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public String getAutorNombre() { return autorNombre; }
    public void setAutorNombre(String autorNombre) { this.autorNombre = autorNombre; }

    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getDestinatarioNombre() { return destinatarioNombre; }
    public void setDestinatarioNombre(String destinatarioNombre) { this.destinatarioNombre = destinatarioNombre; }

    public Long getTrabajoId() { return trabajoId; }
    public void setTrabajoId(Long trabajoId) { this.trabajoId = trabajoId; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
