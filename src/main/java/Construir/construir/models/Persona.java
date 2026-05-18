package Construir.construir.models;

import jakarta.persistence.*;

/**
 * Clase abstracta base del sistema.
 *
 * Representa la información común de cualquier usuario de la plataforma,
 * ya sea un Cliente o un Profesional.
 *
 * Se utiliza herencia JOINED para que:
 * - la información común viva en la tabla "persona"
 * - los datos específicos vivan en tablas hijas como "cliente" y "profesional"
 *
 * IMPORTANTE:
 * - El puntajePromedio se mantiene en Persona porque tanto Cliente como Profesional
 *   pueden recibir calificaciones dentro del sistema.
 * - Se conserva el nombre del atributo "contraseña" para no romper el código actual.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persona")
public abstract class Persona {

    /**
     * Identificador único de la persona.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la persona.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Apellido de la persona.
     */
    @Column(nullable = false, length = 100)
    private String apellido;

    /**
     * Email único del usuario.
     * Se usa también para login.
     */
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    /**
     * Contraseña cifrada del usuario.
     *
     * NOTA:
     * Se mantiene este nombre para no romper el código existente.
     * Más adelante, si querés, la renombramos a "password" o "contrasena"
     * con una migración prolija.
     */
    @Column(name = "contraseña", nullable = false, length = 255)
    private String contraseña;

    /**
     * Teléfono de contacto del usuario.
     */
    @Column(length = 30)
    private String telefono;

    /**
     * Tipo de persona dentro del sistema.
     * Puede ser CLIENTE o PROFESIONAL.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona", nullable = false)
    private TipoPersona tipoPersona;

    /**
     * Puntaje promedio recibido por la persona.
     *
     * Este campo aplica tanto para Cliente como para Profesional,
     * ya que ambos pueden ser calificados.
     */
    @Column(name = "puntaje_promedio")
    private Double puntajePromedio = 0.0;

    // =========================
    // Getters y Setters
    // =========================

    public Long getId() {
        return id;
    }

    /**
     * Setter del ID.
     * Normalmente JPA lo maneja automáticamente.
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    /**
     * Asigna el apellido de la persona.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Asigna el email de la persona.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    /**
     * Asigna la contraseña cifrada.
     *
     * IMPORTANTE:
     * Esta contraseña no debería guardarse en texto plano.
     * Debe llegar ya codificada con BCrypt desde el servicio/controlador.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna el teléfono de contacto.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Asigna el rol del usuario dentro del sistema.
     */
    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Double getPuntajePromedio() {
        return puntajePromedio;
    }

    /**
     * Asigna el promedio de calificaciones recibidas.
     */
    public void setPuntajePromedio(Double puntajePromedio) {
        this.puntajePromedio = puntajePromedio;
    }
}