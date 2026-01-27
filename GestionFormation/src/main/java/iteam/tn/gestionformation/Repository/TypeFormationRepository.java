package iteam.tn.gestionformation.Repository;

import iteam.tn.gestionformation.model.TypeFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFormationRepository
        extends JpaRepository<TypeFormation, Long> {

    boolean existsByNom(String nom);
}
