package Construir.construir.controller;

// ====== IMPORTS DEL MODELO / DTOs / REPOS ======
import Construir.construir.dtos.CalificacionDTO;           // DTO de ENTRADA (solo IDs y datos simples)
import Construir.construir.dtos.CalificacionResponseDTO;   // DTO de SALIDA (respuesta plana sin ciclos JSON)
import Construir.construir.models.Calificacion;            // Entidad Calificacion
import Construir.construir.models.Persona;                 // Entidad abstracta (Cliente/Profesional heredan)
import Construir.construir.models.Cliente;
import Construir.construir.models.Profesional;
import Construir.construir.models.Trabajo;                 // Entidad Trabajo
import Construir.construir.models.EstadoTrabajo;           // Estado del trabajo
import Construir.construir.repositories.CalificacionRepository; // Repo JPA para Calificacion
import Construir.construir.repositories.PersonaRepository;      // Repo JPA para Persona
import Construir.construir.repositories.TrabajoRepository;      // Repo JPA para Trabajo

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para registrar y consultar calificaciones entre usuarios.
 * - Recibe DTOs de entrada para evitar problemas de (de)serialización con herencia (Persona es abstracta).
 * - Devuelve DTOs de salida para no exponer entidades completas ni generar ciclos JSON.
 * - Valida: no autocalificarse y no duplicar calificación para el mismo (autor, destinatario, trabajo).
 * - Ahora admite AMBAS direcciones: CLIENTE→PROFESIONAL y PROFESIONAL→CLIENTE (siempre con trabajo FINALIZADO).
 */
@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    // =========================================================
    // POST /api/calificaciones
    // Crea una calificación (IDs + datos simples).
    // Reglas:
    //   - autorId, destinatarioId y trabajoId obligatorios.
    //   - puntaje entre 1 y 5.
    //   - autor != destinatario.
    //   - trabajo debe estar FINALIZADO.
    //   - dirección válida: (cliente → profesional) o (profesional → cliente).
    //   - sin duplicados (autor, destinatario, trabajo).
    // =========================================================
    @PostMapping
    public ResponseEntity<?> registrarCalificacion(@RequestBody CalificacionDTO dto) {
        // ---- 1) Body básico ----
        if (dto == null || dto.autorId == null || dto.destinatarioId == null || dto.trabajoId == null) {
            return ResponseEntity.badRequest().body("Datos inválidos: autorId, destinatarioId y trabajoId son obligatorios.");
        }
        if (dto.puntaje < 1 || dto.puntaje > 5) {
            return ResponseEntity.badRequest().body("Puntaje fuera de rango: debe estar entre 1 y 5.");
        }
        if (dto.autorId.equals(dto.destinatarioId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No está permitido calificarse a uno mismo.");
        }

        // ---- 2) Buscar entidades ----
        Optional<Persona> autorOpt = personaRepository.findById(dto.autorId);
        Optional<Persona> destOpt  = personaRepository.findById(dto.destinatarioId);
        Optional<Trabajo> trabOpt  = trabajoRepository.findById(dto.trabajoId);

        if (autorOpt.isEmpty() || destOpt.isEmpty() || trabOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró autor/destinatario/trabajo con los IDs provistos.");
        }

        Persona autor = autorOpt.get();
        Persona destinatario = destOpt.get();
        Trabajo trabajo = trabOpt.get();

        // ---- 3) Trabajo FINALIZADO ----
        if (trabajo.getEstado() != EstadoTrabajo.FINALIZADO) {
            return ResponseEntity.badRequest().body("Solo se puede calificar trabajos FINALIZADOS.");
        }
        if (trabajo.getCliente() == null || trabajo.getProfesionalAsignado() == null) {
            return ResponseEntity.badRequest().body("El trabajo no tiene cliente o profesional asignado.");
        }

        // ---- 4) Validar dirección de calificación (dos variantes válidas) ----
        Long clienteId = trabajo.getCliente().getId();
        Long profesionalId = trabajo.getProfesionalAsignado().getId();

        boolean clienteCalificaProfesional =
                (autor instanceof Cliente)
                        && dto.autorId.equals(clienteId)
                        && dto.destinatarioId.equals(profesionalId);

        boolean profesionalCalificaCliente =
                (autor instanceof Profesional)
                        && dto.autorId.equals(profesionalId)
                        && dto.destinatarioId.equals(clienteId);

        if (!clienteCalificaProfesional && !profesionalCalificaCliente) {
            return ResponseEntity.badRequest().body(
                    "Dirección inválida: solo se admite CLIENTE→PROFESIONAL o PROFESIONAL→CLIENTE del mismo trabajo."
            );
        }

        // ---- 5) Duplicados (autor, destinatario, trabajo) ----
        boolean yaExiste = calificacionRepository
                .existsByAutor_IdAndDestinatario_IdAndTrabajo_Id(dto.autorId, dto.destinatarioId, dto.trabajoId);
        if (yaExiste) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe una calificación de este autor para este destinatario en este trabajo.");
        }

        // ---- 6) Persistir ----
        Calificacion cal = new Calificacion();
        cal.setAutor(autor);
        cal.setDestinatario(destinatario);
        cal.setTrabajo(trabajo);
        cal.setPuntaje(dto.puntaje);
        cal.setComentario(dto.comentario);

        Calificacion guardada = calificacionRepository.save(cal);

        // ---- 7) Recalcular promedio del destinatario (si el modelo lo soporta) ----
        tryActualizarPromedioDestinatario(guardada.getDestinatario());

        // ---- 8) Respuesta DTO ----
        return ResponseEntity.ok(toResponseDTO(guardada));
    }

    // =========================================================
    // GET /api/calificaciones/persona/{id}
    // Devuelve TODAS las calificaciones recibidas por una persona como lista de DTOs.
    // =========================================================
    @GetMapping("/persona/{id}")
    public ResponseEntity<?> obtenerCalificacionesDePersona(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }

        List<Calificacion> lista = calificacionRepository.findByDestinatario(persona.get());
        List<CalificacionResponseDTO> resp = lista.stream()
                .map(this::toResponseDTO)
                .toList();

        return ResponseEntity.ok(resp);
    }

    // =========================================================
    // GET /api/calificaciones/persona/{id}/promedio
    // Calcula el promedio de puntajes recibidos por una persona.
    // =========================================================
    @GetMapping("/persona/{id}/promedio")
    public ResponseEntity<?> obtenerPromedioDeCalificaciones(@PathVariable Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada.");
        }

        List<Calificacion> calificaciones = calificacionRepository.findByDestinatario(persona.get());
        if (calificaciones.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        double promedio = calificaciones.stream()
                .mapToInt(Calificacion::getPuntaje)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok(promedio);
    }

    // ==============================
    // ==== PRIVADOS
    // ==============================

    private CalificacionResponseDTO toResponseDTO(Calificacion c) {
        String autorNombre = (c.getAutor() == null) ? null :
                (safe(c.getAutor().getNombre()) + " " + safe(c.getAutor().getApellido())).trim();
        String destNombre = (c.getDestinatario() == null) ? null :
                (safe(c.getDestinatario().getNombre()) + " " + safe(c.getDestinatario().getApellido())).trim();

        Long trabajoId = (c.getTrabajo() != null) ? c.getTrabajo().getId() : null;

        return new CalificacionResponseDTO(
                c.getId(),
                c.getAutor() != null ? c.getAutor().getId() : null,
                (autorNombre != null && !autorNombre.isBlank()) ? autorNombre : null,
                c.getDestinatario() != null ? c.getDestinatario().getId() : null,
                (destNombre != null && !destNombre.isBlank()) ? destNombre : null,
                trabajoId,
                c.getPuntaje(),
                c.getComentario(),
                c.getFecha()
        );
    }

    private void tryActualizarPromedioDestinatario(Persona destinatario) {
        try {
            List<Calificacion> calificaciones = calificacionRepository.findByDestinatario(destinatario);
            double promedio = calificaciones.isEmpty()
                    ? 0.0
                    : calificaciones.stream().mapToInt(Calificacion::getPuntaje).average().orElse(0.0);

            destinatario.setPuntajePromedio(promedio); // si el modelo no lo tiene, el catch lo ignora
            personaRepository.save(destinatario);

        } catch (Throwable ignored) { }
    }

    private String safe(String s) { return (s == null) ? "" : s; }
}
