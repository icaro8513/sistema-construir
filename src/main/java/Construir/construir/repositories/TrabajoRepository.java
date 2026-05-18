package Construir.construir.repositories;

import Construir.construir.models.Trabajo;
import Construir.construir.models.Cliente;
import Construir.construir.models.Profesional;
import Construir.construir.models.EstadoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Trabajo.
 * Además de los métodos CRUD por defecto, definimos algunas búsquedas
 * específicas por cliente y por profesional.
 */
public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {

    /**
     * Todos los trabajos creados por un cliente.
     */
    List<Trabajo> findByCliente_Id(Long clienteId);

    /**
     * Trabajos asignados a un profesional, filtrando por estado.
     * Lo usamos para que el profesional vea sus trabajos EN_PROCESO.
     */
    List<Trabajo> findByProfesionalAsignadoAndEstado(Profesional profesional, EstadoTrabajo estado);
}
