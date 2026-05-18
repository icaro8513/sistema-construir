package Construir.construir.models;

import jakarta.persistence.*;

/**
 * Entidad Profesional del sistema.
 *
 * Hereda de Persona y agrega los datos propios del profesional:
 * - especialidad
 * - experiencia
 * - direccion
 *
 * IMPORTANTE:
 * - Ya NO se guarda calificacionPromedio en esta clase.
 * - El promedio ahora queda centralizado en Persona,
 *   porque tanto Cliente como Profesional pueden recibir calificaciones.
 */
@Entity
@Table(name = "profesional")
@PrimaryKeyJoinColumn(name = "id") // Usa la PK heredada de Persona con estrategia JOINED
public class Profesional extends Persona {

    /**
     * Especialidad del profesional.
     * Ejemplo: ELECTRICISTA, PLOMERO, GASISTA, etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidad especialidad;

    /**
     * Experiencia del profesional.
     *
     * Por ahora se deja como texto libre para no romper tu sistema actual.
     * Más adelante podemos decidir si conviene:
     * - dejarlo descriptivo, o
     * - convertirlo en años de experiencia numéricos.
     */
    @Column(length = 500)
    private String experiencia;

    /**
     * Dirección del profesional.
     *
     * Se usa en el registro y en la información de perfil.
     */
    @Column(length = 255)
    private String direccion;

    // =========================
    // Getters y Setters
    // =========================

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * Asigna la especialidad del profesional.
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getExperiencia() {
        return experiencia;
    }

    /**
     * Asigna la experiencia del profesional.
     */
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getDireccion() {
        return direccion;
    }

    /**
     * Asigna la dirección del profesional.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}