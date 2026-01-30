package ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import iteam.tn.gestionformation.Dto.ParticipationCreateDto;
import iteam.tn.gestionformation.GestionFormationApplication;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.controller.ParticipantContoller; // Vérifie l'orthographe (Controller)
import iteam.tn.gestionformation.model.Participation;
import iteam.tn.gestionformation.service.ParticipationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ContextConfiguration(classes = GestionFormationApplication.class)
@ExtendWith(MockitoExtension.class)

@WebMvcTest(ParticipantContoller.class)
@DisplayName("Tests unitaires du Controller Participant")
class ParticipantContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParticipationService participationService;

    @MockBean
    private AuthClient authClient;

    @Test
    @DisplayName("SÉCURITÉ : Création réussie de participations")
    void creerParticipations_success() throws Exception {
        // Given
        ParticipationCreateDto dto = new ParticipationCreateDto();
        dto.setSessionId(1L);
        dto.setUserIds(List.of(10, 11));

        when(authClient.getUserIdConnecte(anyString()))
                .thenReturn(Map.of("idService", 2, "idAgent", 5));

        when(participationService.creerParticipations(eq(1L), anyList(), eq(2), eq(5)))
                .thenReturn(List.of(new Participation(), new Participation()));

        // When & Then
        mockMvc.perform(post("/api/participant/CreateParticipants")
                        .header("Authorization", "Bearer valid_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.response").isArray());
    }

    @Test
    @DisplayName("❌ Cas Erreur Service : Doit renvoyer code 0")
    void creerParticipations_serviceException() throws Exception {
        // 1. Mock de l'auth (nécessaire pour arriver jusqu'au service)
        when(authClient.getUserIdConnecte(anyString()))
                .thenReturn(Map.of("idService", 1, "idAgent", 5));

        // 2. Mock du service qui simule une erreur (Ligne 49 de ton controller)
        when(participationService.creerParticipations(anyLong(), anyList(), anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Erreur base de données"));

        String body = """
    {
      "sessionId": 1,
      "userIds": [10, 20]
    }
    """;

        // 3. Exécution
        mockMvc.perform(post("/api/participant/CreateParticipants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token-test")
                        .content(body))
                .andExpect(status().isOk()) // Le try-catch transforme l'erreur en réponse 200
                .andExpect(jsonPath("$.code").value(0)) // ✅ On vérifie "code" et non "status"
                .andExpect(jsonPath("$.response").isEmpty()); // ✅ On vérifie "data" est null/vide
    }
    @Test
    @DisplayName("ERREUR : Token invalide ou vide")
    void creerParticipations_authClientException() throws Exception {
        // Given
        ParticipationCreateDto dto = new ParticipationCreateDto();
        dto.setSessionId(1L);
        dto.setUserIds(List.of(10));

        // Simuler un échec de l'AuthClient (ex: FeignException ou Runtime)
        when(authClient.getUserIdConnecte(any()))
                .thenThrow(new RuntimeException("Token invalide"));

        // When & Then
        mockMvc.perform(post("/api/participant/CreateParticipants")
                        .header("Authorization", "Bearer badtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    @DisplayName("ERREUR : Body JSON malformé")
    void creerParticipations_emptyBody() throws Exception {
        // On envoie un JSON vide ou invalide
        mockMvc.perform(post("/api/participant/CreateParticipants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
}