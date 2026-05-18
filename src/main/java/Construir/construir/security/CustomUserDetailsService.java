package Construir.construir.security;

import Construir.construir.models.Persona;
import Construir.construir.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Carga usuarios por email desde la base para que Spring Security pueda autenticarlos.
 * - Username = email
 * - Password = contraseña cifrada (BCrypt) que guardamos en Persona
 * - Authorities = ROLE_CLIENTE o ROLE_PROFESIONAL (según TipoPersona)
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Armamos el rol como autoridad de Spring (ROLE_CLIENTE / ROLE_PROFESIONAL)
        String rolSpring = "ROLE_" + persona.getTipoPersona().name(); // p.ej. ROLE_CLIENTE
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rolSpring));

        return new User(
                persona.getEmail(),           // username
                persona.getContraseña(),      // password (BCrypt)
                authorities
        );
    }
}
