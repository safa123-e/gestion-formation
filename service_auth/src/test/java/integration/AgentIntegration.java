package integration;


import com.demo.FormationServiceApplication;
import com.demo.model.AgentGa;
import com.demo.repository.AgentGaRepository;
import com.demo.repository.AgentProfilAppGaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(classes = FormationServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test d'intégration - AgentController")
class AgentIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgentGaRepository userRepository; // On injecte le vrai repo
    @Autowired
    private AgentProfilAppGaRepository agentProfilRepo;
    @Autowired
    private ObjectMapper objectMapper;

    private Integer idAhmed;
    private Integer idSafae;

    @BeforeEach
    void setUp() {
        // Nettoyage et insertion réelle en base de données H2
        agentProfilRepo.deleteAll();   // enfant d'abord
        userRepository.deleteAll();    // parent ensuite
        AgentGa ahmed = new AgentGa();
        ahmed.setNomPrenomAgentGa("Ahmed");
        ahmed = userRepository.save(ahmed); // On récupère l'ID généré par la BDD
        idAhmed = ahmed.getIdAgentGa();

        AgentGa safae = new AgentGa();
        safae.setNomPrenomAgentGa("Safae");
        safae = userRepository.save(safae);
        idSafae = safae.getIdAgentGa();
    }

    @Test
    @DisplayName("Récupération réelle des agents par IDs via l'API")
    void shouldReturnAgentsFromDatabase() throws Exception {
        // GIVEN
        List<Integer> idsToSearch = Arrays.asList(idAhmed, idSafae);

        // WHEN & THEN
        mockMvc.perform(post("/agents/by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idsToSearch)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.nomPrenomAgentGa == 'Ahmed')]").exists())
                .andExpect(jsonPath("$[?(@.nomPrenomAgentGa == 'Safae')]").exists());
    }
}