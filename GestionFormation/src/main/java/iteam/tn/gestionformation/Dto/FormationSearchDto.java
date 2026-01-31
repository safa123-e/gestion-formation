package iteam.tn.gestionformation.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FormationSearchDto {

    private String titre;
    private Boolean statut;
    private Long typeFormationId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    // getters & setters
}

