package ControllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import iteam.tn.gestionformation.Dto.SessionCreateDto;
import iteam.tn.gestionformation.GestionFormationApplication;
import iteam.tn.gestionformation.controller.SessionController;
import iteam.tn.gestionformation.model.SessionFormation;
import iteam.tn.gestionformation.service.SessionFormationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@ContextConfiguration(classes = GestionFormationApplication.class)

@WebMvcTest(SessionController.class)
@DisplayName("Tests Unitaires - Session Controller")
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionFormationService sessionService;

    @Test
    @DisplayName("✅ Succès : Création d'une session")
    void creerSession_Success() throws Exception {
        // Given
        SessionCreateDto dto = new SessionCreateDto();
        // Remplis ton DTO ici selon ses champs (ex: titre, date...)

        SessionFormation sessionCreee = new SessionFormation();
        sessionCreee.setId(10L);

        when(sessionService.creerSession(any(SessionCreateDto.class))).thenReturn(sessionCreee);

        // When & Then
        mockMvc.perform(post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.response.id").value(10)); // Utilisation de .response
    }

    @Test
    @DisplayName("❌ Erreur : Exception lors de la création")
    void creerSession_Error() throws Exception {
        // Given
        when(sessionService.creerSession(any())).thenThrow(new RuntimeException("Erreur DB"));

        // When & Then
        mockMvc.perform(post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.response").isEmpty());
    }

    @Test
    @DisplayName("✅ Succès : Récupération des notifications")
    void getNotifications_Success() throws Exception {
        // Given
        SessionFormation s1 = new SessionFormation();
        s1.setId(1L);
        SessionFormation s2 = new SessionFormation();
        s2.setId(2L);

        List<SessionFormation> sessions = Arrays.asList(s1, s2);

        when(sessionService.getNotifications(anyString())).thenReturn(sessions);

        // When & Then
        mockMvc.perform(get("/api/sessions/notifications")
                        .header("Authorization", "Bearer token-test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1));
    }
}