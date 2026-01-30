package iteam.tn.gestionformation.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FormationCreateDto {

    private String titre;
    private String description;
    private Boolean statut; // DRAFT, ACTIVE
    @JsonProperty("typeFormationId")
    private Long typeFormationId;
}
