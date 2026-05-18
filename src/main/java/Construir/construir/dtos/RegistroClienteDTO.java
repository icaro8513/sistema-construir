package Construir.construir.dtos;

/** DTO de entrada para registrar un Cliente */
public class RegistroClienteDTO {
    public String nombre;
    public String apellido;
    public String email;
    public String password;   // cruda en el request; se cifra en el server
    public String telefono;
    public String direccion;
}
