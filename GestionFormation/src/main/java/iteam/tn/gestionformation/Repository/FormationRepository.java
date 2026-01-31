package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository
        extends JpaRepository<Formation, Long>,
        JpaSpecificationExecutor<Formation> {

    List<Formation> findByStatut(Boolean statut);

    List<Formation> findByTypeFormationId(Long typeFormationId);
}
