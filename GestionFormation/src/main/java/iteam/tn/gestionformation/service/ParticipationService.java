package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Dto.FormationUserDto;
import iteam.tn.gestionformation.Repository.ParticipationRepository;
import iteam.tn.gestionformation.Repository.SessionFormationRepository;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.Participation;
import iteam.tn.gestionformation.model.SessionFormation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    private final SessionFormationRepository sessionFormationRepository;

    @Transactional
    public List<Participation> creerParticipations(
            Long sessionId,
            List<Integer> utilisateurIds,
            Integer serviceId,
            Integer utilisateurConnecteId) {

        SessionFormation session = sessionFormationRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session introuvable"));

        List<Participation> participations = new ArrayList<>();

        for (Integer utilisateurId : utilisateurIds) {

            // éviter doublon
            if (participationRepository
                    .existsBySessionAndUtilisateurId(session, utilisateurId)) {
                continue;
            }

            Participation p = new Participation();
            p.setSession(session);
            p.setUtilisateurId(utilisateurId);
            p.setServiceId(serviceId);
            p.setDateDemande(LocalDateTime.now());
            p.setDecideParUtilisateurId(utilisateurConnecteId);
            participations.add(p);
        }

        return participationRepository.saveAll(participations);
    }

    public List<FormationUserDto> formationsParUtilisateur(Integer utilisateurId) {

        List<Participation> participations =
                participationRepository.findByUtilisateurId(utilisateurId);

        return participations.stream().map(p -> {

            SessionFormation s = p.getSession();
            Formation f = s.getFormation();

            return new FormationUserDto(
                    f.getId(),
                    f.getTitre(),
                    s.getId(),
                    s.getDateDebut(),
                    s.getDateFin(),
                    p.getStatut()
            );

        }).toList();
    }
}
