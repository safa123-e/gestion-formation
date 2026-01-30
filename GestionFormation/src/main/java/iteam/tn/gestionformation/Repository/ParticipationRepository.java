package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import iteam.tn.gestionformation.model.SessionFormation  ;
import java.util.List;

@Repository
public interface ParticipationRepository
        extends JpaRepository<Participation, Integer> {
    List<Participation> findByUtilisateurIdAndStatutIsNull(Integer utilisateurId);
    int countBySessionId(Long sessionId); // pour savoir si une session a déjà des participants

    List<Participation> findByUtilisateurId(Integer utilisateurId);

    List<Participation> findBySessionId(Long sessionId);

    List<Participation> findByServiceId(Integer serviceId);

    List<Participation> findByStatut(Boolean statut);
    boolean existsBySessionAndUtilisateurId(
SessionFormation session,
            Integer utilisateurId
    );
    boolean existsByUtilisateurIdAndSessionId(Integer utilisateurId, Integer sessionId);
}

