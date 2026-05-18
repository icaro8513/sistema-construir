package Construir.construir.dtos;

/** DTO de salida para login (devolveremos el JWT aquí más adelante) */
public class LoginResponseDTO {
    public String token;     // lo completaremos cuando emitamos JWT
    public String email;
    public String rol;       // CLIENTE o PROFESIONAL

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, String email, String rol) {
        this.token = token;
        this.email = email;
        this.rol = rol;
    }
}
