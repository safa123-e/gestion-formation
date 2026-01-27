package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.AffectationFormationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectationFormationServiceRepository
        extends JpaRepository<AffectationFormationService, Long> {

    List<AffectationFormationService> findByServiceId(Long serviceId);

    List<AffectationFormationService> findBySessionId(Long sessionId);
}
