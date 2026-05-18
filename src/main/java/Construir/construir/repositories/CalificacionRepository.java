package Construir.construir.repositories;

import Construir.construir.models.Calificacion;
import Construir.construir.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a las calificaciones guardadas en la base de datos
 */
@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    // Buscar todas las calificaciones que recibió una persona (cliente o profesional)
    List<Calificacion> findByDestinatario(Persona destinatario);
    // ¿Existe ya una calificación (autor -> destinatario) para un trabajo?
    boolean existsByAutor_IdAndDestinatario_IdAndTrabajo_Id(Long autorId, Long destinatarioId, Long trabajoId);
}
