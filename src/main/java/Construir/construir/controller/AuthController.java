package Construir.construir.controller;

import Construir.construir.dtos.LoginRequestDTO;
import Construir.construir.dtos.LoginResponseDTO;
import Construir.construir.dtos.RegistroClienteDTO;
import Construir.construir.dtos.RegistroProfesionalDTO;
import Construir.construir.dtos.RegistroResponseDTO;

import Construir.construir.models.Cliente;
import Construir.construir.models.Especialidad;
import Construir.construir.models.Persona;
import Construir.construir.models.Profesional;
import Construir.construir.models.TipoPersona;

import Construir.construir.repositories.PersonaRepository;

import Construir.construir.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints publicos de autenticación/registro.
 * - Registro de Cliente y Profesional con contraseña cifrada (BCrypt).
 * - Login con email/password que devuelve un JWT.
 *
 * Requisitos:
 *  - SecurityConfig: PasswordEncoder (BCrypt), /api/auth/** permitAll, csrf disable.
 *  - PersonaRepository: findByEmail(String), existsByEmail(String).
 *  - JwtUtil: generateToken(email, rol, userId).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;       // BCrypt

    @Autowired
    private AuthenticationManager authenticationManager; // definido en SecurityConfig

    @Autowired
    private JwtUtil jwtUtil;

    // =========================================================================
    // POST /api/auth/registro/cliente
    // Crea un Cliente. Valida:
    //  - Campos obligatorios
    //  - Email único
    //  - Contraseña con mínimo 8 caracteres y al menos una mayúscula
    // =========================================================================
    @PostMapping("/registro/cliente")
    public ResponseEntity<?> registrarCliente(@RequestBody RegistroClienteDTO dto) {
        if (dto == null || isBlank(dto.nombre) || isBlank(dto.apellido) ||
                isBlank(dto.email) || isBlank(dto.password)) {
            return ResponseEntity.badRequest()
                    .body("Campos obligatorios: nombre, apellido, email, password.");
        }

        // Validar reglas de password
        if (!esPasswordValida(dto.password)) {
            return ResponseEntity.badRequest()
                    .body("La contraseña debe tener al menos 8 caracteres y una letra mayúscula.");
        }

        if (personaRepository.existsByEmail(dto.email)) {
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }

        // Mapeo DTO -> entidad
        Cliente c = new Cliente();
        c.setNombre(dto.nombre.trim());
        c.setApellido(dto.apellido.trim());
        c.setEmail(dto.email.trim());
        c.setContraseña(passwordEncoder.encode(dto.password)); // Hash BCrypt
        c.setTelefono(dto.telefono);
        c.setDireccion(dto.direccion);
        c.setTipoPersona(TipoPersona.CLIENTE);

        // Persistir
        Cliente guardado = (Cliente) personaRepository.save(c);

        // Respuesta "plana"
        return ResponseEntity.ok(
                new RegistroResponseDTO(
                        guardado.getId(),
                        "CLIENTE",
                        dto.email,
                        "Registro de cliente exitoso."
                )
        );
    }

    // =========================================================================
    // POST /api/auth/registro/profesional
    // Crea un Profesional. Valida:
    //  - Campos obligatorios
    //  - Email único
    //  - Contraseña con mínimo 8 caracteres y al menos una mayúscula
    //  - Especialidad válida (enum Especialidad)
    // =========================================================================
    @PostMapping("/registro/profesional")
    public ResponseEntity<?> registrarProfesional(@RequestBody RegistroProfesionalDTO dto) {
        if (dto == null || isBlank(dto.nombre) || isBlank(dto.apellido) ||
                isBlank(dto.email) || isBlank(dto.password)) {
            return ResponseEntity.badRequest()
                    .body("Campos obligatorios: nombre, apellido, email, password.");
        }

        // Validar reglas de password
        if (!esPasswordValida(dto.password)) {
            return ResponseEntity.badRequest()
                    .body("La contraseña debe tener al menos 8 caracteres y una letra mayúscula.");
        }

        if (personaRepository.existsByEmail(dto.email)) {
            return ResponseEntity.badRequest().body("El email ya está registrado.");
        }

        // Parseo de especialidad (enum)
        Especialidad esp;
        if (!isBlank(dto.especialidad)) {
            try {
                esp = Especialidad.valueOf(dto.especialidad.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest()
                        .body("especialidad inválida. Use un valor del enum Especialidad.");
            }
        } else {
            return ResponseEntity.badRequest().body("especialidad es obligatoria.");
        }

        // Mapeo DTO -> entidad
        Profesional p = new Profesional();
        p.setNombre(dto.nombre.trim());
        p.setApellido(dto.apellido.trim());
        p.setEmail(dto.email.trim());
        p.setContraseña(passwordEncoder.encode(dto.password)); // Hash BCrypt
        p.setTelefono(dto.telefono);
        p.setDireccion(dto.direccion);
        p.setTipoPersona(TipoPersona.PROFESIONAL);
        p.setEspecialidad(esp);
        p.setExperiencia(dto.experiencia);

        // Persistir
        Profesional guardado = (Profesional) personaRepository.save(p);

        // Respuesta "plana"
        return ResponseEntity.ok(
                new RegistroResponseDTO(
                        guardado.getId(),
                        "PROFESIONAL",
                        dto.email,
                        "Registro de profesional exitoso."
                )
        );
    }

    // =========================================================================
    // POST /api/auth/login
    // Autentica email/password y devuelve un JWT con:
    //  - subject = email
    //  - claim "rol"   = CLIENTE o PROFESIONAL
    //  - claim "userId" = id de Persona
    // =========================================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        if (dto == null || isBlank(dto.email) || isBlank(dto.password)) {
            return ResponseEntity.badRequest().body("email y password son obligatorios.");
        }

        // 1) Autenticar credenciales contra UserDetailsService + PasswordEncoder
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email, dto.password)
        );

        // 2) Cargar persona para claims (rol, userId)
        Persona persona = personaRepository.findByEmail(dto.email.trim()).orElse(null);
        if (persona == null) {
            // No debería pasar si Authentication fue OK, pero por seguridad:
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }

        String rol = persona.getTipoPersona().name(); // CLIENTE o PROFESIONAL
        Long userId = persona.getId();

        // 3) Generar token JWT
        String token = jwtUtil.generateToken(persona.getEmail(), rol, userId);

        // 4) Responder con el token + datos mínimos
        return ResponseEntity.ok(new LoginResponseDTO(token, persona.getEmail(), rol));
    }

    // ---------------- utils internos ----------------

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Reglas de contraseña:
     *  - No nula
     *  - Longitud mínima: 8 caracteres
     *  - Al menos una letra mayúscula
     */
    private boolean esPasswordValida(String password) {
        if (password == null) return false;
        String trimmed = password.trim();
        if (trimmed.length() < 8) return false;

        boolean tieneMayuscula = false;
        for (char c : trimmed.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
                break;
            }
        }
        return tieneMayuscula;
    }
}
