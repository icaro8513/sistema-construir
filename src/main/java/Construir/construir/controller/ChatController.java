package Construir.construir.controller;

import Construir.construir.dtos.MensajeChatDTO;
import Construir.construir.models.*;
import Construir.construir.repositories.MensajeChatRepository;
import Construir.construir.repositories.PersonaRepository;
import Construir.construir.repositories.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Chat entre Cliente y Profesional para un trabajo ya aceptado (EN_PROCESO).
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private MensajeChatRepository mensajeChatRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    // =========================================================
    // GET /api/chat/trabajo/{trabajoId}
    // Lista todos los mensajes del chat de ese trabajo (orden ascendente).
    // =========================================================
    @GetMapping("/trabajo/{trabajoId}")
    public ResponseEntity<?> obtenerMensajes(@PathVariable Long trabajoId) {
        Optional<Trabajo> trabOpt = trabajoRepository.findById(trabajoId);
        if (trabOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Trabajo trabajo = trabOpt.get();

        // Sólo tiene sentido el chat si el trabajo está EN_PROCESO
        if (trabajo.getEstado() != EstadoTrabajo.EN_PROCESO &&
                trabajo.getEstado() != EstadoTrabajo.FINALIZADO) {
            return ResponseEntity.badRequest()
                    .body("El chat sólo está disponible para trabajos EN_PROCESO o FINALIZADO.");
        }

        List<MensajeChatDTO> mensajes = mensajeChatRepository
                .findByTrabajoOrderByEnviadoEnAsc(trabajo)
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(mensajes);
    }

    // =========================================================
    // POST /api/chat
    // Enviar un nuevo mensaje.
    //
    // Body (MensajeChatDTO con):
    //  - trabajoId (obligatorio)
    //  - remitenteId (obligatorio)
    //  - contenido (obligatorio)
    //  - remitenteRol (CLIENTE o PROFESIONAL, se valida contra el trabajo)
    // =========================================================
    @PostMapping
    public ResponseEntity<?> enviarMensaje(@RequestBody MensajeChatDTO dto) {
        if (dto == null || dto.trabajoId == null || dto.remitenteId == null ||
                dto.contenido == null || dto.contenido.trim().isEmpty() ||
                dto.remitenteRol == null || dto.remitenteRol.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Campos obligatorios: trabajoId, remitenteId, remitenteRol, contenido.");
        }

        Optional<Trabajo> trabOpt = trabajoRepository.findById(dto.trabajoId);
        if (trabOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Trabajo no encontrado.");
        }
        Trabajo trabajo = trabOpt.get();

        // El chat sólo aplica si el trabajo está EN_PROCESO o FINALIZADO
        if (trabajo.getEstado() != EstadoTrabajo.EN_PROCESO &&
                trabajo.getEstado() != EstadoTrabajo.FINALIZADO) {
            return ResponseEntity.badRequest()
                    .body("El chat sólo está disponible para trabajos EN_PROCESO o FINALIZADO.");
        }

        // Buscar remitente
        Optional<Persona> remOpt = personaRepository.findById(dto.remitenteId);
        if (remOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Remitente no encontrado.");
        }
        Persona remitente = remOpt.get();

        // Validar que el remitente sea el cliente del trabajo o el profesional asignado
        Persona cliente = trabajo.getCliente();
        Persona profesional = trabajo.getProfesionalAsignado();

        boolean esCliente = (cliente != null && cliente.getId().equals(remitente.getId()));
        boolean esProfesional = (profesional != null && profesional.getId().equals(remitente.getId()));

        if (!esCliente && !esProfesional) {
            return ResponseEntity.badRequest().body("El remitente no pertenece a este trabajo.");
        }

        String rolEsperado = esCliente ? "CLIENTE" : "PROFESIONAL";
        if (!rolEsperado.equalsIgnoreCase(dto.remitenteRol)) {
            return ResponseEntity.badRequest()
                    .body("remitenteRol no coincide con el rol real del remitente para este trabajo.");
        }

        MensajeChat mensaje = new MensajeChat();
        mensaje.setTrabajo(trabajo);
        mensaje.setRemitente(remitente);
        mensaje.setRemitenteRol(rolEsperado.toUpperCase());
        mensaje.setContenido(dto.contenido.trim());

        MensajeChat guardado = mensajeChatRepository.save(mensaje);
        return ResponseEntity.ok(toDTO(guardado));
    }

    // ====================== helpers ==========================

    private MensajeChatDTO toDTO(MensajeChat m) {
        String nombre = "";
        if (m.getRemitente() != null) {
            String n = m.getRemitente().getNombre();
            String a = m.getRemitente().getApellido();
            nombre = ((n != null ? n : "") + " " + (a != null ? a : "")).trim();
        }

        return new MensajeChatDTO(
                m.getId(),
                m.getTrabajo() != null ? m.getTrabajo().getId() : null,
                m.getRemitente() != null ? m.getRemitente().getId() : null,
                nombre.isBlank() ? null : nombre,
                m.getRemitenteRol(),
                m.getContenido(),
                m.getEnviadoEn()
        );
    }
}
