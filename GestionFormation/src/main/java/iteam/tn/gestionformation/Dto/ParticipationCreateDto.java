package iteam.tn.gestionformation.Dto;

import lombok.Data;

import java.util.List;
@Data
public class ParticipationCreateDto {

    private Long sessionId;
    private List<Integer> userIds;

    // getters & setters
}
