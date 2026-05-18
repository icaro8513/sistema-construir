package Construir.construir.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Mensaje de chat entre cliente y profesional para un Trabajo.
 */
@Entity
@Table(name = "mensaje_chat")
public class MensajeChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Conversacion siempre asociada a un trabajo concreto
    @ManyToOne
    @JoinColumn(name = "trabajo_id", nullable = false)
    private Trabajo trabajo;

    // Quien envia el mensaje (Cliente o Profesional)
    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Persona remitente;

    // CLIENTE o PROFESIONAL (para facilitar en el front)
    @Column(name = "remitente_rol", length = 20, nullable = false)
    private String remitenteRol;

    @Column(nullable = false, length = 1000)
    private String contenido;

    @Column(name = "enviado_en", nullable = false)
    private LocalDateTime enviadoEn;

    @PrePersist
    public void prePersist() {
        if (enviadoEn == null) {
            enviadoEn = LocalDateTime.now();
        }
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Persona getRemitente() {
        return remitente;
    }

    public void setRemitente(Persona remitente) {
        this.remitente = remitente;
    }

    public String getRemitenteRol() {
        return remitenteRol;
    }

    public void setRemitenteRol(String remitenteRol) {
        this.remitenteRol = remitenteRol;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getEnviadoEn() {
        return enviadoEn;
    }

    public void setEnviadoEn(LocalDateTime enviadoEn) {
        this.enviadoEn = enviadoEn;
    }
}
