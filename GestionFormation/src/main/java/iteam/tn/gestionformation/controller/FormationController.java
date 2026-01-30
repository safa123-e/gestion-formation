package iteam.tn.gestionformation.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.comp.ApiResponse;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.service.FormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
@SecurityRequirement(name = "bearerAuth")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {
    private static final Logger logger = LoggerFactory.getLogger(FormationController.class);

    private final FormationService formationService;
    private final AuthClient authClient ;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Formation> creerFormation(
            @RequestBody FormationCreateDto dto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
            try{
        logger.info("DTO reçu : {}", dto);
        logger.info("Authorization : {}", authHeader);

        // Récupérer l'ID de l'utilisateur connecté via Feign
            Map<String, Object> userMap = authClient.getUserIdConnecte(authHeader);
            logger.info("userMap récupéré depuis AuthClient : {}", userMap);

            Integer userId = null;
            Object idAgentObj = userMap.get("idAgent");

            if (idAgentObj instanceof Integer) {
                userId = (Integer) idAgentObj;
            } else if (idAgentObj instanceof String) {
                // Cas où le service renvoie un String, on tente la conversion
                userId = Integer.parseInt((String) idAgentObj);
            }

            logger.info("ID utilisateur connecté : {}", userId);

            Formation formation = formationService.creerFormation(dto, userId);
            logger.info("Formation créée avec succès : {}", formation);

            return new ApiResponse<>(1, formation);

        } catch (Exception e) {
            logger.error("Erreur lors de la création de la formation", e);
            return new ApiResponse<>(0, null);
        }
    }


}
