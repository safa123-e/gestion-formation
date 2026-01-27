package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.SessionFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionFormationRepository
        extends JpaRepository<SessionFormation, Long> {

    List<SessionFormation> findByFormationId(Long formationId);
}
