package Construir.construir.dtos;

/**
 * DTO para crear una calificación sin exponer entidades completas.
 */
public class CalificacionDTO {
    public Long autorId;
    public Long destinatarioId;
    public Long trabajoId;
    public int puntaje;
    public String comentario;
}
