package Construir.construir.controller;

// ==== IMPORTS NECESARIOS ====
import Construir.construir.models.Cliente;
import Construir.construir.models.Profesional;
import Construir.construir.models.Persona;
import Construir.construir.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personas") // Todas las rutas comenzarán con /api/personas
public class PersonaController {

    // ==== INYECCIÓN DEL REPOSITORIO ====
    @Autowired
    private PersonaRepository personaRepository;

    // ============ [SECCIÓN CLIENTES] ============

    // ✅ POST: Crear nuevo cliente
    @PostMapping("/cliente")
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return personaRepository.save(cliente);
    }

    // ✅ GET: Listar todos los clientes
    @GetMapping("/clientes")
    public List<Cliente> obtenerClientes() {
        return personaRepository.findAll()
                .stream()
                .filter(persona -> persona instanceof Cliente)
                .map(persona -> (Cliente) persona)
                .collect(Collectors.toList());
    }

    // ============ [SECCIÓN PROFESIONALES] ============

    // ✅ POST: Crear nuevo profesional
    @PostMapping("/profesional")
    public Profesional crearProfesional(@RequestBody Profesional profesional) {
        return personaRepository.save(profesional);
    }

    // ✅ GET: Listar todos los profesionales
    @GetMapping("/profesionales")
    public List<Profesional> obtenerProfesionales() {
        return personaRepository.findAll()
                .stream()
                .filter(persona -> persona instanceof Profesional)
                .map(persona -> (Profesional) persona)
                .collect(Collectors.toList());
    }
}


