package iteam.tn.gestionformation.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.Dto.FormationSearchDto;
import iteam.tn.gestionformation.comp.ApiResponse;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.service.FormationService;
import iteam.tn.gestionformation.service.TypeFormationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import iteam.tn.gestionformation.model.TypeFormation ;
import java.util.List;
import java.util.Map;
//@SecurityRequirement(name = "bearerAuth")
@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/formations")
@RequiredArgsConstructor
public class FormationController {
    private static final Logger logger = LoggerFactory.getLogger(FormationController.class);
    private final TypeFormationService typeFormationService;
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
    @GetMapping("/allTypeFormation")
    public List<TypeFormation>  getAllTypeFormation() {
        try {
            System.out.println("[GET] /allTypeFormation appelé");

            List<TypeFormation> types = typeFormationService.listerTypes();

            if (types == null || types.isEmpty()) {
                System.out.println("Aucun type de formation trouvé");
                return null;
            }

            System.out.println("Types trouvés : " + types.size());
            return types;

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des types de formation");
            e.printStackTrace();

            return null ;

        }



    }
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(formationService.getById(id));
    }
    @PutMapping("/{id}")
    public ApiResponse<Formation> update(
            @PathVariable Long id,
            @RequestBody FormationCreateDto dto) {
        try {
            // Ajout de 'new' et fermeture correcte de la méthode
            return new ApiResponse<>(1,formationService.update(id, dto));
        } catch (Exception e) {
            // Correction de la syntaxe catch et du message de log
            logger.error("Erreur lors de la mise à jour de la formation avec l'ID : {}", id, e);
            // Retourne un code d'erreur (0) ou un message d'erreur
            return new ApiResponse<>(0, null);
        }
    }


    @PostMapping("/search")
    public ResponseEntity<List<Formation>> search(@RequestBody FormationSearchDto dto) {
        return ResponseEntity.ok(formationService.search(dto));
    }

}
