package iteam.tn.gestionformation.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SessionCreateDto {

    private Long formationId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String formateur;
    private Integer capacite;

    // ✅ liste des services à affecter
    private List<Long> serviceIds;
}
