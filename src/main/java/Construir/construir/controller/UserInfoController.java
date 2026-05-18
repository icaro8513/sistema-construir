package Construir.construir.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoint de prueba en zona protegida. Requiere JWT válido.
 */
@RestController
@RequestMapping("/api/secure")
public class UserInfoController {

    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI(Authentication auth) {
        // auth.getName() será el email que pusimos como principal
        return ResponseEntity.ok(
                java.util.Map.of(
                        "username", auth != null ? auth.getName() : null
                )
        );
    }
}
