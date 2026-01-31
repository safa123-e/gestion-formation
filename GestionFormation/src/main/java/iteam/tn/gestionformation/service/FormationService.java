package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.Dto.FormationSearchDto;
import iteam.tn.gestionformation.Repository.FormationRepository;
import iteam.tn.gestionformation.Repository.TypeFormationRepository;
import iteam.tn.gestionformation.Specification.FormationSpecification;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.TypeFormation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormationService {

    private final FormationRepository formationRepository;
    private final TypeFormationRepository typeFormationRepository;
    private final AuthClient authClient ;

    public Formation creerFormation(FormationCreateDto dto, Integer adminId) {
        try {


            TypeFormation type = typeFormationRepository.findById(dto.getTypeFormationId())
                    .orElseThrow(() ->
                            new RuntimeException("Type de formation introuvable avec id : " + dto.getTypeFormationId())
                    );

            Formation formation = Formation.builder()
                    .titre(dto.getTitre())
                    .description(dto.getDescription())
                    .typeFormation(type)
                    .dateCreation(LocalDateTime.now())
                    .creeParAdminId(adminId)
                    .statut(dto.getStatut())
                    .build();

            return formationRepository.save(formation);

        } catch (RuntimeException e) {
            // erreurs métier (type introuvable, auth, etc.)
            throw e;

        } catch (Exception e) {
            // erreurs techniques imprévues
            throw new RuntimeException("Erreur lors de la création de la formation", e);
        }
    }
    public Formation getById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));
    }

    public Formation update(Long id, FormationCreateDto dto) {
        Formation f = getById(id);

        f.setTitre(dto.getTitre());
        f.setDescription(dto.getDescription());
        f.setStatut(dto.getStatut());
        f.setTypeFormation(
                typeFormationRepository.findById(dto.getTypeFormationId()).orElseThrow()
        );

        return formationRepository.save(f);
    }

    public List<Formation> consulterFormations() {
        return formationRepository.findAll();
    }

    public void supprimerFormation(Long id) {
        formationRepository.deleteById(id);
    }
    public List<Formation> search(FormationSearchDto dto) {
        return formationRepository.findAll(
                FormationSpecification.withCriteria(dto)
        );
    }
}
