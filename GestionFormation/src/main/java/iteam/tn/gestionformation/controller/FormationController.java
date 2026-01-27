package iteam.tn.gestionformation.controller;

import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.comp.ApiResponse;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.service.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {

    private final FormationService formationService;

    @PostMapping
    public ApiResponse<Formation> creerFormation(
            @RequestBody FormationCreateDto dto) {

        try {
            Formation formation = formationService.creerFormation(dto);

            return
                    new ApiResponse<>(1, formation);


        } catch (Exception e) {
            return
                    new ApiResponse<>(0,null);

        }
    }


}
