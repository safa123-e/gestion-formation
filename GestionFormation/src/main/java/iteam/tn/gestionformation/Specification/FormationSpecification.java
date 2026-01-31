package iteam.tn.gestionformation.Specification;

import iteam.tn.gestionformation.Dto.FormationSearchDto;
import iteam.tn.gestionformation.model.Formation ;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class FormationSpecification {

    public static Specification<Formation> withCriteria(FormationSearchDto dto) {

        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // titre LIKE
            if (dto.getTitre() != null && !dto.getTitre().isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("titre")),
                                "%" + dto.getTitre().toLowerCase() + "%"));
            }

            // statut =
            if (dto.getStatut() != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("statut"), dto.getStatut()));
            }

            // type formation
            if (dto.getTypeFormationId() != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("typeFormation").get("id"),
                                dto.getTypeFormationId()));
            }

            // date from
            if (dto.getDateDebut() != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(
                                root.get("dateCreation"),
                                dto.getDateDebut()));
            }

            // date to
            if (dto.getDateFin() != null) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(
                                root.get("dateCreation"),
                                dto.getDateFin()));
            }

            return predicate;
        };
    }
}

