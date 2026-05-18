package Construir.construir.dtos;

/** DTO de salida común para registro */
public class RegistroResponseDTO {
    public Long id;
    public String rol;      // CLIENTE o PROFESIONAL
    public String email;
    public String mensaje;

    public RegistroResponseDTO(Long id, String rol, String email, String mensaje) {
        this.id = id;
        this.rol = rol;
        this.email = email;
        this.mensaje = mensaje;
    }
}
