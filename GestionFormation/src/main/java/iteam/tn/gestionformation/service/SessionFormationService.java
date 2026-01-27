package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Dto.SessionCreateDto;
import iteam.tn.gestionformation.Repository.FormationRepository;
import iteam.tn.gestionformation.Repository.SessionFormationRepository;
import iteam.tn.gestionformation.model.AffectationFormationService;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.SessionFormation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionFormationService {


    private final SessionFormationRepository sessionRepository;
    private final FormationRepository formationRepository;
    private final SessionFormationRepository repository;

    public SessionFormation creerSession(SessionCreateDto dto) {
        try {

            Formation formation = formationRepository.findById(dto.getFormationId())
                    .orElseThrow(() -> new RuntimeException("Formation introuvable avec id : " + dto.getFormationId()));

            SessionFormation session = SessionFormation.builder()
                    .formation(formation)
                    .dateDebut(dto.getDateDebut())
                    .dateFin(dto.getDateFin())
                    .lieu(dto.getLieu())
                    .formateur(dto.getFormateur())
                    .capacite(dto.getCapacite())
                    .build();

            // ✅ création des affectations
            List<AffectationFormationService> affectations =
                    dto.getServiceIds().stream()
                            .map(serviceId ->
                                    AffectationFormationService.builder()
                                            .session(session)
                                            .serviceId(serviceId)
                                            .dateAffectation(LocalDateTime.now())
                                            .build()
                            ).toList();

            session.setAffectations(affectations);

            return sessionRepository.save(session);

        } catch (RuntimeException e) {
            // erreurs métier connues
            throw e;

        } catch (Exception e) {
            // erreurs techniques inattendues
            throw new RuntimeException("Erreur lors de la création de la session de formation", e);
        }
    }

    public List<SessionFormation> sessionsParFormation(Long formationId) {
        return repository.findByFormationId(formationId);
    }
}
