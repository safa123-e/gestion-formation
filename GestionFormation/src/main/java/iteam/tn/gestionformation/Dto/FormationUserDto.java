package iteam.tn.gestionformation.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FormationUserDto {

    private Long formationId;
    private String titreFormation;

    private Long sessionId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    private Boolean statutParticipation;
}

