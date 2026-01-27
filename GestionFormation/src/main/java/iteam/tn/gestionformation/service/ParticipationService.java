package iteam.tn.gestionformation.service;

import iteam.tn.gestionformation.Dto.FormationUserDto;
import iteam.tn.gestionformation.Repository.ParticipationRepository;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.Participation;
import iteam.tn.gestionformation.model.SessionFormation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public List<FormationUserDto> formationsParUtilisateur(Long utilisateurId) {

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
