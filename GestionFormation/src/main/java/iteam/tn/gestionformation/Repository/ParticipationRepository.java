package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository
        extends JpaRepository<Participation, Long> {

    List<Participation> findByUtilisateurId(Long utilisateurId);

    List<Participation> findBySessionId(Long sessionId);

    List<Participation> findByServiceId(Long serviceId);

    List<Participation> findByStatut(Boolean statut);

    boolean existsByUtilisateurIdAndSessionId(Long utilisateurId, Long sessionId);
}

