package Construir.construir.dtos;

/** DTO de entrada para registrar un Profesional */
public class RegistroProfesionalDTO {
    public String nombre;
    public String apellido;
    public String email;
    public String password;   // cruda en el request; se cifra en el server
    public String telefono;
    public String direccion;

    public String especialidad; // nombre del enum (ELECTRICISTA, GASISTA, etc.)
    public String experiencia;  // texto libre
}
