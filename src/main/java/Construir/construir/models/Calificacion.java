package Construir.construir.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

/**
 * Esta clase representa una calificación entre personas (cliente ↔ profesional)
 * después de un trabajo realizado. Implementa validaciones y asignación automática de fecha.
 */
/**
 * Calificación entre personas por un trabajo concreto.
 * Evita duplicados con una restricción única (autor, destinatario, trabajo).
 */
@Entity
@Table(
        name = "calificacion",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_calificacion_autor_destinatario_trabajo",
                        columnNames = {"autor_id", "destinatario_id", "trabajo_id"}
                )
        }
)
public class Calificacion {

    // 🔹 ID autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Persona que emite la calificación (cliente o profesional)
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Persona autor;

    // 🔹 Persona que recibe la calificación (cliente o profesional)
    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Persona destinatario;

    //  Trabajo relacionado a la calificacion
    @ManyToOne
    @JoinColumn(name = "trabajo_id", nullable = false)
    private Trabajo trabajo;

    //  Puntaje de 1 a 5 (validado automaticamente con Bean Validation)
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int puntaje;

    //  Comentario opcional del autor
    @Column(length = 500)
    private String comentario;

    //  Fecha en que se realizo la calificacion (se setea automaticamente al guardar)
    private LocalDate fecha;

    // === METODOS AUTOMATICOS ===

    /**
     * Asigna automáticamente la fecha actual cuando se crea una nueva calificación.
     */
    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDate.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public Persona getAutor() {
        return autor;
    }

    public void setAutor(Persona autor) {
        this.autor = autor;
    }

    public Persona getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Persona destinatario) {
        this.destinatario = destinatario;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}


