package Construir.construir.controller;

import Construir.construir.dtos.TrabajoDTO;
import Construir.construir.dtos.TrabajoResponseDTO;
import Construir.construir.dtos.CotizacionDTO;
import Construir.construir.dtos.CotizacionResponseDTO;

import Construir.construir.models.*;
import Construir.construir.repositories.CotizacionRepository;
import Construir.construir.repositories.PersonaRepository;
import Construir.construir.repositories.TrabajoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de Trabajos + Postulaciones.
 *
 * Maneja:
 *  - publicación de trabajos por parte del cliente;
 *  - listado de trabajos;
 *  - detalle de trabajo;
 *  - cotizaciones/postulaciones de profesionales;
 *  - aceptación de cotizaciones;
 *  - finalización de trabajos;
 *  - baja lógica de trabajos;
 *  - listado de trabajos asignados a un profesional.
 *
 * NUEVO:
 *  - Ahora también recibe y devuelve la especialidad requerida del trabajo.
 */
@RestController
@RequestMapping("/api/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    // =========================================================
    // POST /api/trabajos
    // Crea un trabajo nuevo publicado por un Cliente.
    // =========================================================
    @PostMapping
    public ResponseEntity<?> crearTrabajo(@RequestBody TrabajoDTO dto) {

        // Validación básica del DTO recibido
        if (dto == null || dto.clienteId == null || dto.titulo == null || dto.titulo.isBlank()) {
            return ResponseEntity.badRequest().body("Datos inválidos: clienteId y titulo son obligatorios.");
        }

        // Buscar persona por ID
        Optional<Persona> personaOpt = personaRepository.findById(dto.clienteId);

        // Validar que exista y que sea Cliente
        if (personaOpt.isEmpty() || !(personaOpt.get() instanceof Cliente)) {
            return ResponseEntity.badRequest().body("clienteId inválido: no existe o no es un Cliente.");
        }

        Cliente cliente = (Cliente) personaOpt.get();

        // Crear entidad Trabajo
        Trabajo t = new Trabajo();
        t.setCliente(cliente);
        t.setTitulo(dto.titulo.trim());
        t.setDescripcion(dto.descripcion);
        t.setMedidas(dto.medidas);
        t.setUbicacion(dto.ubicacion);

        // NUEVO:
        // Convertir la especialidad requerida recibida como texto
        // al enum Especialidad.
        try {
            t.setEspecialidadRequerida(parseEspecialidadRequerida(dto.especialidadRequerida));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    "especialidadRequerida inválida. Valores permitidos: " + especialidadesValidas()
            );
        }

        // Estado inicial del trabajo.
        // Por defecto será PUBLICADO.
        EstadoTrabajo estado = EstadoTrabajo.PUBLICADO;

        if (dto.estado != null && !dto.estado.isBlank()) {
            try {
                estado = EstadoTrabajo.valueOf(dto.estado.trim().toUpperCase());
            } catch (Exception ignored) {
                estado = EstadoTrabajo.PUBLICADO;
            }
        }

        t.setEstado(estado);

        // Guardar trabajo
        Trabajo guardado = trabajoRepository.save(t);

        // Devolver DTO plano al frontend
        return ResponseEntity.ok(toTrabajoDTO(guardado));
    }

    // =========================================================
    // GET /api/trabajos
    // Lista todos los trabajos en forma de DTO.
    // =========================================================
    @GetMapping
    public ResponseEntity<?> listarTrabajos() {
        List<TrabajoResponseDTO> lista = trabajoRepository.findAll().stream()
                .map(this::toTrabajoDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    // =========================================================
    // GET /api/trabajos/{id}
    // Devuelve un trabajo por ID en formato DTO.
    // =========================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTrabajo(@PathVariable Long id) {
        return trabajoRepository.findById(id)
                .<ResponseEntity<?>>map(t -> ResponseEntity.ok(toTrabajoDTO(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // =========================================================
    // GET /api/trabajos/{id}/postulaciones
    // Lista cotizaciones de un trabajo.
    // =========================================================
    @GetMapping("/{id}/postulaciones")
    public ResponseEntity<?> listarPostulaciones(@PathVariable Long id) {

        // Validar que el trabajo exista
        if (!trabajoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Buscar cotizaciones del trabajo y devolver DTO
        return ResponseEntity.ok(
                cotizacionRepository.findByTrabajo_Id(id).stream()
                        .map(this::toCotizacionDTO)
                        .toList()
        );
    }

    // =========================================================
    // POST /api/trabajos/{id}/postulaciones
    // Crea una cotización de un Profesional para un Trabajo.
    // =========================================================
    @PostMapping("/{id}/postulaciones")
    public ResponseEntity<?> crearCotizacion(@PathVariable Long id, @RequestBody CotizacionDTO dto) {

        // Validación básica
        if (dto == null || dto.profesionalId == null || dto.monto == null) {
            return ResponseEntity.badRequest().body("Datos inválidos: profesionalId y monto son obligatorios.");
        }

        // Buscar trabajo
        Optional<Trabajo> trabajoOpt = trabajoRepository.findById(id);

        if (trabajoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trabajo trabajo = trabajoOpt.get();

        // Solo se puede cotizar un trabajo publicado
        if (trabajo.getEstado() != EstadoTrabajo.PUBLICADO) {
            return ResponseEntity.badRequest().body("El trabajo no admite nuevas postulaciones porque no está PUBLICADO.");
        }

        // Buscar profesional
        Optional<Persona> personaOpt = personaRepository.findById(dto.profesionalId);

        if (personaOpt.isEmpty() || !(personaOpt.get() instanceof Profesional)) {
            return ResponseEntity.badRequest().body("profesionalId inválido: no existe o no es un Profesional.");
        }

        Profesional profesional = (Profesional) personaOpt.get();

        // Evitar que un mismo profesional se postule dos veces al mismo trabajo
        boolean yaExiste = cotizacionRepository.existsByTrabajo_IdAndProfesional_Id(
                trabajo.getId(),
                profesional.getId()
        );

        if (yaExiste) {
            return ResponseEntity.badRequest().body("Ya existe una cotización de este profesional para este trabajo.");
        }

        // Crear cotización
        Cotizacion c = new Cotizacion();
        c.setTrabajo(trabajo);
        c.setProfesional(profesional);
        c.setMonto(dto.monto);

        // NUEVO:
        // Guardamos la moneda enviada por el frontend.
        // Si viene null o vacía, queda ARS por defecto.
        try {
            c.setMoneda(parseMonedaCotizacion(dto.moneda));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Moneda inválida. Valores permitidos: ARS o USD.");
        }

        c.setMensaje(dto.mensaje);
        c.setEstado(EstadoCotizacion.PENDIENTE);
        // Guardar la cotización en la base de datos
        Cotizacion guardada = cotizacionRepository.save(c);

     // Devolver respuesta al frontend
        return ResponseEntity.ok(toCotizacionDTO(guardada));
    }

    // =========================================================
    // POST /api/trabajos/postulaciones/{cotizacionId}/aceptar
    // Acepta una cotización.
    // =========================================================
    @PostMapping("/postulaciones/{cotizacionId}/aceptar")
    public ResponseEntity<?> aceptarCotizacion(@PathVariable Long cotizacionId) {

        // Buscar cotización
        Optional<Cotizacion> cotOpt = cotizacionRepository.findById(cotizacionId);

        if (cotOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cotizacion cot = cotOpt.get();
        Trabajo trabajo = cot.getTrabajo();

        // Validar que la cotización tenga trabajo asociado
        if (trabajo == null) {
            return ResponseEntity.badRequest().body("La cotización no está asociada a un trabajo válido.");
        }

        // Solo se puede aceptar si el trabajo sigue publicado
        if (trabajo.getEstado() != EstadoTrabajo.PUBLICADO) {
            return ResponseEntity.badRequest().body("El trabajo ya no admite aceptación de cotizaciones.");
        }

        // Asignar profesional elegido al trabajo
        trabajo.setProfesionalAsignado(cot.getProfesional());

        // Pasar trabajo a EN_PROCESO
        trabajo.setEstado(EstadoTrabajo.EN_PROCESO);

        // Marcar cotización aceptada
        cot.setEstado(EstadoCotizacion.ACEPTADA);
        cotizacionRepository.save(cot);

        // Rechazar las demás cotizaciones del mismo trabajo
        List<Cotizacion> delMismoTrabajo = cotizacionRepository.findByTrabajo_Id(trabajo.getId());

        for (Cotizacion otra : delMismoTrabajo) {
            if (!otra.getId().equals(cot.getId())) {
                otra.setEstado(EstadoCotizacion.RECHAZADA);
                cotizacionRepository.save(otra);
            }
        }

        // Guardar trabajo actualizado
        trabajoRepository.save(trabajo);

        return ResponseEntity.ok(toCotizacionDTO(cot));
    }

    // =========================================================
    // POST /api/trabajos/{trabajoId}/finalizar
    // Finaliza un trabajo en proceso.
    // =========================================================
    @PostMapping("/{trabajoId}/finalizar")
    public ResponseEntity<?> finalizarTrabajo(@PathVariable Long trabajoId) {

        // Buscar trabajo
        Optional<Trabajo> trabOpt = trabajoRepository.findById(trabajoId);

        if (trabOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trabajo trabajo = trabOpt.get();

        // Solo se puede finalizar si está EN_PROCESO
        if (trabajo.getEstado() != EstadoTrabajo.EN_PROCESO) {
            return ResponseEntity.badRequest().body("Solo se puede finalizar un trabajo que esté EN_PROCESO.");
        }

        // Debe tener profesional asignado
        if (trabajo.getProfesionalAsignado() == null) {
            return ResponseEntity.badRequest().body("No hay profesional asignado al trabajo.");
        }

        trabajo.setEstado(EstadoTrabajo.FINALIZADO);
        trabajoRepository.save(trabajo);

        return ResponseEntity.ok(toTrabajoDTO(trabajo));
    }

    // =========================================================
    // POST /api/trabajos/{trabajoId}/baja
    // Baja lógica de un trabajo.
    // =========================================================
    @PostMapping("/{trabajoId}/baja")
    public ResponseEntity<?> bajaLogica(@PathVariable Long trabajoId) {

        // Buscar trabajo
        Optional<Trabajo> trabOpt = trabajoRepository.findById(trabajoId);

        if (trabOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Trabajo trabajo = trabOpt.get();
        EstadoTrabajo estado = trabajo.getEstado();

        // No se puede dar de baja si ya está en proceso o finalizado
        if (estado == EstadoTrabajo.EN_PROCESO || estado == EstadoTrabajo.FINALIZADO) {
            return ResponseEntity.badRequest()
                    .body("No se puede dar de baja: el trabajo ya está en proceso o finalizado.");
        }

        // Rechazar todas las cotizaciones asociadas
        List<Cotizacion> cotizaciones = cotizacionRepository.findByTrabajo_Id(trabajoId);

        for (Cotizacion c : cotizaciones) {
            c.setEstado(EstadoCotizacion.RECHAZADA);
        }

        cotizacionRepository.saveAll(cotizaciones);

        // Poner estado BAJA
        trabajo.setEstado(EstadoTrabajo.BAJA);
        trabajoRepository.save(trabajo);

        return ResponseEntity.ok(toTrabajoDTO(trabajo));
    }

    // =========================================================
    // GET /api/trabajos/profesional/{profesionalId}/asignados
    // Devuelve trabajos EN_PROCESO asignados a un profesional.
    // =========================================================
    @GetMapping("/profesional/{profesionalId}/asignados")
    public ResponseEntity<?> listarTrabajosAsignados(@PathVariable Long profesionalId) {

        // Validar profesional
        Optional<Persona> personaOpt = personaRepository.findById(profesionalId);

        if (personaOpt.isEmpty() || !(personaOpt.get() instanceof Profesional)) {
            return ResponseEntity.badRequest()
                    .body("profesionalId inválido: no existe o no es un Profesional.");
        }

        Profesional profesional = (Profesional) personaOpt.get();

        // Buscar trabajos en proceso asignados a ese profesional
        List<TrabajoResponseDTO> lista = trabajoRepository
                .findByProfesionalAsignadoAndEstado(profesional, EstadoTrabajo.EN_PROCESO)
                .stream()
                .map(this::toTrabajoDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    // =========================================================
    // HELPERS DE MAPEO
    // =========================================================

    /**
     * Convierte una entidad Trabajo a DTO de respuesta.
     */
    private TrabajoResponseDTO toTrabajoDTO(Trabajo t) {
        Persona cliente = t.getCliente();
        Profesional pro = t.getProfesionalAsignado();

        String clienteNombre = (cliente == null) ? null :
                ((safe(cliente.getNombre()) + " " + safe(cliente.getApellido())).trim());

        String proNombre = (pro == null) ? null :
                ((safe(pro.getNombre()) + " " + safe(pro.getApellido())).trim());

        return new TrabajoResponseDTO(
                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getMedidas(),
                t.getUbicacion(),

                // NUEVO: devolver especialidad requerida al frontend
                (t.getEspecialidadRequerida() != null ? t.getEspecialidadRequerida().name() : null),

                (t.getEstado() != null ? t.getEstado().name() : null),
                (cliente != null ? cliente.getId() : null),
                (clienteNombre != null && !clienteNombre.isBlank() ? clienteNombre : null),
                (pro != null ? pro.getId() : null),
                (proNombre != null && !proNombre.isBlank() ? proNombre : null)
        );
    }

    /**
     * Convierte una entidad Cotizacion a DTO de salida.
     */
    /**
     * Convierte una entidad Cotizacion a DTO de salida.
     */
    private CotizacionResponseDTO toCotizacionDTO(Cotizacion c) {
        return new CotizacionResponseDTO(
                c.getId(),
                (c.getTrabajo() != null ? c.getTrabajo().getId() : null),
                (c.getProfesional() != null ? c.getProfesional().getId() : null),
                c.getMonto(),

                // NUEVO:
                // Devuelve ARS o USD al frontend.
                (c.getMoneda() != null ? c.getMoneda().name() : "ARS"),

                c.getMensaje(),
                (c.getEstado() != null ? c.getEstado().name() : null),
                c.getCreadaEn()
        );
    }

    // =========================================================
    // HELPERS DE VALIDACIÓN
    // =========================================================

    /**
     * Convierte el texto recibido desde el frontend a enum Especialidad.
     *
     * Si viene null o vacío:
     *  - retorna null;
     *  - significa que el cliente no está seguro o acepta cualquier especialidad.
     */
    private Especialidad parseEspecialidadRequerida(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        String normalizado = valor.trim().toUpperCase()
                .replace(" ", "_")
                .replace("-", "_");

        // Permite recibir ALBANIL sin ñ desde el frontend.
        if (normalizado.equals("ALBANIL")) {
            normalizado = "ALBAÑIL";
        }

        return Especialidad.valueOf(normalizado);
    }

    /**
     * Devuelve las especialidades válidas para mostrar mensajes de error claros.
     */
    private String especialidadesValidas() {
        return java.util.Arrays.stream(Especialidad.values())
                .map(Enum::name)
                .toList()
                .toString();
    }

    /**
     * Convierte el texto recibido desde frontend a enum MonedaCotizacion.
     *
     * Si viene null o vacío:
     *  - retorna ARS por defecto.
     *
     * Valores válidos:
     *  - ARS
     *  - USD
     */
    private MonedaCotizacion parseMonedaCotizacion(String valor) {
        if (valor == null || valor.isBlank()) {
            return MonedaCotizacion.ARS;
        }

        String normalizado = valor.trim().toUpperCase();

        return MonedaCotizacion.valueOf(normalizado);
    }

    /**
     * Evita NullPointerException al armar nombres.
     */
    private String safe(String s) {
        return (s == null) ? "" : s;
    }


}