package ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.GestionFormationApplication;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.controller.FormationController;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.service.FormationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ContextConfiguration(classes = GestionFormationApplication.class)
@WebMvcTest(FormationController.class)
@DisplayName("Tests Unitaires - Formation Controller")
class FormationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FormationService formationService;

    @MockBean
    private AuthClient authClient;
    @Test
    void testConversionIdAgentString() throws Exception {
        Map<String, Object> userMap = Map.of("idAgent", "99"); // On envoie un String
        when(authClient.getUserIdConnecte(anyString())).thenReturn(userMap);

        mockMvc.perform(post("/api/formations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titre\":\"Test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));
    }





    @Test
    @DisplayName("✅ Succès : Création d'une formation avec idAgent (Integer)")
    void creerFormation_Success_IntegerId() throws Exception {
        // Given
        FormationCreateDto dto = new FormationCreateDto();
        dto.setTitre("Java Spring Boot");
        dto.setDescription("Formation avancée");

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("idAgent", 123); // Cas Integer

        Formation formationCreee = new Formation();
        formationCreee.setId(1L);
        formationCreee.setTitre(dto.getTitre());

        when(authClient.getUserIdConnecte(anyString())).thenReturn(userMap);
        when(formationService.creerFormation(any(FormationCreateDto.class), eq(123)))
                .thenReturn(formationCreee);

        // When & Then
        mockMvc.perform(post("/api/formations")
                        .header("Authorization", "Bearer token-valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
               .andExpect(jsonPath("$.response.titre").value("Java Spring Boot"));
    }


    @Test
    @DisplayName("✅ Succès : Création d'une formation avec idAgent (String)")
    void creerFormation_Success_StringId() throws Exception {
        // Given
        FormationCreateDto dto = new FormationCreateDto();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("idAgent", "456"); // Cas String (sera casté en Integer par ton code)

        when(authClient.getUserIdConnecte(anyString())).thenReturn(userMap);
        when(formationService.creerFormation(any(), eq(456))).thenReturn(new Formation());

        // When & Then
        mockMvc.perform(post("/api/formations")
                        .header("Authorization", "Bearer token-valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));
    }

    @Test
    @DisplayName("❌ Erreur : Exception lors de la récupération de l'utilisateur")
    void creerFormation_AuthError() throws Exception {
        // Given
        when(authClient.getUserIdConnecte(any()))
                .thenThrow(new RuntimeException("Service Auth indisponible"));

        // When & Then
        mockMvc.perform(post("/api/formations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.response").isEmpty());
    }

    @Test
    @DisplayName("❌ Erreur : Exception dans le service Formation")
    void creerFormation_ServiceError() throws Exception {
        // Given
        Map<String, Object> userMap = Map.of("idAgent", 1);
        when(authClient.getUserIdConnecte(anyString())).thenReturn(userMap);

        when(formationService.creerFormation(any(), anyInt()))
                .thenThrow(new RuntimeException("Erreur DB"));

        // When & Then
        mockMvc.perform(post("/api/formations")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titre\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
}
