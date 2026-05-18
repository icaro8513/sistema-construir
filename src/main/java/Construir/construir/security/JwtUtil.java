package Construir.construir.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * Utilidad para emitir y validar JWT HMAC-SHA256.
 * Lee la clave y la expiración desde application.properties.
 *
 * Propiedades esperadas:
 *  - jwt.secret=una_clave_larga_de_32+_caracteres
 *  - jwt.expiration-ms=86400000   (opcional, 24h por defecto)
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:86400000}") // 24h por defecto
    private long expirationMs;

    /** Construye la key HMAC a partir del secret. Debe tener al menos 32 bytes. */
    private Key signingKey() {
        // si truena, verifica que jwt.secret sea bien largo
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un JWT con:
     *   - subject = email (usuario)
     *   - claims extra: rol, userId
     *   - expiración: ahora + expirationMs
     */
    public String generateToken(String email, String rol, Long userId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(Map.of(
                        "rol", rol,
                        "userId", userId
                ))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** Obtiene todos los claims o lanza excepción si el token no es válido. */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Devuelve el "subject" del token, que en nuestro caso es el email. */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /** Valida que el token no esté expirado. */
    public boolean isTokenValid(String token) {
        try {
            return extractAllClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Valida el token contra un UserDetails:
     *   - que el username del token coincida
     *   - que no esté expirado
     *
     * Es el método que usa JwtAuthenticationFilter.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username != null
                    && username.equals(userDetails.getUsername())
                    && isTokenValid(token);
        } catch (Exception e) {
            return false;
        }
    }
}
