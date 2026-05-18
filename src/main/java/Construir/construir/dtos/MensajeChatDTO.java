package Construir.construir.dtos;

import java.time.LocalDateTime;

/**
 * DTO para enviar/recibir mensajes de chat.
 */
public class MensajeChatDTO {

    public Long id;
    public Long trabajoId;
    public Long remitenteId;
    public String remitenteNombre;
    public String remitenteRol;   // CLIENTE o PROFESIONAL
    public String contenido;
    public LocalDateTime enviadoEn;

    public MensajeChatDTO() {
    }

    public MensajeChatDTO(Long id,
                          Long trabajoId,
                          Long remitenteId,
                          String remitenteNombre,
                          String remitenteRol,
                          String contenido,
                          LocalDateTime enviadoEn) {
        this.id = id;
        this.trabajoId = trabajoId;
        this.remitenteId = remitenteId;
        this.remitenteNombre = remitenteNombre;
        this.remitenteRol = remitenteRol;
        this.contenido = contenido;
        this.enviadoEn = enviadoEn;
    }
}
