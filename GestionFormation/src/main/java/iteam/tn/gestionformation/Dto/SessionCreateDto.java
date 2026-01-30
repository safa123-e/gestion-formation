package iteam.tn.gestionformation.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
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
    private Integer capaciteTotale; // Capacité globale de la session

    // ✅ On remplace List<Long> par une liste d'objets structurés
    private List<ServiceCapacityDto> services;
}


