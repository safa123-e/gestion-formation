package iteam.tn.gestionformation.Dto;

import lombok.Data;

@Data
public class FormationCreateDto {

    private String titre;
    private String description;
    private Boolean statut; // DRAFT, ACTIVE
    private Long typeFormationId;
}
