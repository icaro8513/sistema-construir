package Construir.construir.config;

import Construir.construir.security.CustomUserDetailsService;
import Construir.construir.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // PÁGINAS PÚBLICAS (sin token)
                        .requestMatchers(
                                "/", "/index.html",
                                "/cliente.html",                 // panel cliente
                                "/profesional.html",             // panel profesional
                                "/cliente-trabajo.html",        // <- NUEVA vista “Mis trabajos (cliente)”
                                "/profesional-calificaciones.html", // <- (opcional) vista reputación profesional
                                "/css/**", "/js/**", "/images/**", "/favicon.ico"
                        ).permitAll()

                        // Endpoints publicos de auth (login/registro)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Endpoints que requieren JWT
                        .requestMatchers("/api/secure/**").authenticated()
                        .requestMatchers("/api/trabajos/**").authenticated()
                        .requestMatchers("/api/calificaciones/**").authenticated()
                        .requestMatchers("/api/chat/**").authenticated()

                        // Todo lo demás, lo dejamos pasar
                        .anyRequest().permitAll()
                )
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
