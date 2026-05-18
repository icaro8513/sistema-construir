package Construir.construir.repositories;

import Construir.construir.models.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    // Todas las cotizaciones de un trabajo
    List<Cotizacion> findByTrabajo_Id(Long trabajoId);

    // Para evitar que un profesional se postule 2 veces al mismo trabajo
    boolean existsByTrabajo_IdAndProfesional_Id(Long trabajoId, Long profesionalId);
}


