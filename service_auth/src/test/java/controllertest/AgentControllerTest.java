package controllertest;
import static org.hamcrest.Matchers.hasSize;
import com.demo.controller.AgentController;
import com.demo.dto.AgentGaDto;
import com.demo.services.AgentGaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de AgentController - Récupération par IDs")
class AgentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AgentGaService userService;

    @InjectMocks
    private AgentController agentController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        // Initialisation de MockMvc en mode standalone
        mockMvc = MockMvcBuilders.standaloneSetup(agentController).build();
    }

    @Test
    @DisplayName("Succès - Récupérer des agents par une liste d'IDs")
    void getUsersByIds_Success() throws Exception {
        // GIVEN (Préparation)
        List<Integer> ids = Arrays.asList(1, 2);

        AgentGaDto agent1 = new AgentGaDto();
        agent1.setNomPrenomAgentGa("Ahmed");

        AgentGaDto agent2 = new AgentGaDto();
        agent2.setNomPrenomAgentGa("Safae");

        List<AgentGaDto> expectedList = Arrays.asList(agent1, agent2);

        when(userService.findByIds(anyIterable())).thenReturn(expectedList);

        // WHEN & THEN (Action & Vérification)
        mockMvc.perform(post("/agents/by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // Pour éviter le problème XML vu précédemment
                        .content(objectMapper.writeValueAsString(ids)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nomPrenomAgentGa").value("Ahmed"))
                .andExpect(jsonPath("$[1].nomPrenomAgentGa").value("Safae"));
    }

    @Test
    @DisplayName("Échec - Liste vide en entrée")
    void getUsersByIds_EmptyList() throws Exception {
        // GIVEN
        List<Integer> emptyIds = Collections.emptyList();

        // On s'assure que le service renvoie bien une liste vide et PAS null
        when(userService.findByIds(anyIterable())).thenReturn(Collections.emptyList());

        // WHEN & THEN
        mockMvc.perform(post("/agents/by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // Force le JSON
                        .content(objectMapper.writeValueAsString(emptyIds)))
                .andDo(print()) // REGARDE LE "Body" DANS TA CONSOLE
                .andExpect(status().isOk())
                // Utilise cette syntaxe plus robuste :
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
    @DisplayName("Échec - Erreur interne du service")
    void getUsersByIds_InternalServerError() {
        // GIVEN
        List<Integer> ids = Arrays.asList(1);
        when(userService.findByIds(anyIterable())).thenThrow(new RuntimeException("Database error"));

        // THEN (On vérifie que l'exécution lance bien la RuntimeException)
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(post("/agents/by-ids")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ids)));
        });
    }
}