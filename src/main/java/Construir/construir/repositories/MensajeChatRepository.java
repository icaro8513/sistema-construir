package Construir.construir.repositories;

import Construir.construir.models.MensajeChat;
import Construir.construir.models.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeChatRepository extends JpaRepository<MensajeChat, Long> {

    List<MensajeChat> findByTrabajoOrderByEnviadoEnAsc(Trabajo trabajo);
}
