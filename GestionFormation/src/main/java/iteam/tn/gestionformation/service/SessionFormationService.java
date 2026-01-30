package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Dto.SessionCreateDto;
import iteam.tn.gestionformation.Repository.AffectationFormationServiceRepository;
import iteam.tn.gestionformation.Repository.FormationRepository;
import iteam.tn.gestionformation.Repository.ParticipationRepository;
import iteam.tn.gestionformation.Repository.SessionFormationRepository;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.model.AffectationFormationService;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.SessionFormation;
import iteam.tn.gestionformation.model.Participation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionFormationService {



    private final SessionFormationRepository sessionRepository;
    private final FormationRepository formationRepository;
    private final AffectationFormationServiceRepository affectationRepo;
    private final ParticipationRepository participationRepo;
    private final AuthClient authClient; // pour récupérer info utilisateur

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
                    .capacite(dto.getCapaciteTotale())
                    .build();

            // ✅ création des affectations
            List<AffectationFormationService> affectations =
                    dto.getServices().stream()
                            .map(service ->
                                    AffectationFormationService.builder()
                                            .session(session)
                                            .serviceId(service.getServiceId())
                                            .capacite(service.getCapacite())
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

    public List<SessionFormation> getNotifications(String authHeader) {
        // Récupérer l'utilisateur connecté depuis le service Auth
        HashMap<String, Object> userInfo = (HashMap<String, Object>) authClient.getUserIdConnecte(authHeader);
        Integer userId = (Integer) userInfo.get("idAgent");
        Integer serviceId = (Integer) userInfo.get("idService");
        Boolean isResponsable = (Boolean) userInfo.get("isResponsable");

        if (isResponsable) {
            // Responsable : toutes les sessions de son service sans aucun participant
            List<AffectationFormationService> sessionsService = affectationRepo.findByServiceId(serviceId);

            return sessionsService.stream()
                    .filter(a -> participationRepo.countBySessionId(a.getSession().getId()) == 0)
                    .map(AffectationFormationService::getSession)
                    .toList();
        } else {
            // Agent normal : ses participations avec statut null
            List<Participation> participations = participationRepo
                    .findByUtilisateurIdAndStatutIsNull(userId);

            return participations.stream()
                    .map(Participation::getSession)
                    .toList();
        }
    }
}

