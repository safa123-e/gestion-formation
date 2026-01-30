package iteam.tn.gestionformation.Dto;

import lombok.Data;

@Data
public  class ServiceCapacityDto {
    private Long serviceId;
    private Integer capacite; // Capacité spécifique pour ce service
}