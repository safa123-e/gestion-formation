/*
 * package integration;
 * 
 * 
 * import org.junit.jupiter.api.*; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
 * import org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.http.MediaType; import
 * org.springframework.test.context.ActiveProfiles; import
 * org.springframework.test.web.servlet.MockMvc; import
 * com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * import tn.isie.auth.model.AgentGa; import tn.isie.auth.model.EtatAgentGa;
 * import tn.isie.auth.repository.AgentGaRepository;
 * 
 * import static
 * org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import
 * static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 * import static org.assertj.core.api.Assertions.*;
 * 
 * @SpringBootTest
 * 
 * @AutoConfigureMockMvc
 * 
 * @ActiveProfiles("test")
 * 
 * @DisplayName("Tests d'intégration agent")
 * 
 * @TestMethodOrder(MethodOrderer.OrderAnnotation.class) class
 * UserIntegrationTest {
 * 
 * @Autowired private MockMvc mockMvc;
 * 
 * @Autowired private AgentGaRepository userRepository;
 * 
 * @Autowired private ObjectMapper objectMapper;
 * 
 * @BeforeEach void setUp() { userRepository.deleteAll(); }
 * 
 * @Test
 * 
 * @Order(1)
 * 
 * @DisplayName("Scénario complet: CRUD utilisateur") void fullCrudScenario()
 * throws Exception { EtatAgentGa etat = new EtatAgentGa();
 * etat.setIdEtatAgentGa(1); etat.setLibEtatAgentGa("active"); // 1. CREATE
 * AgentGa newUser = new AgentGa(1, "Ahmed",
 * "ahmed@email.com",true,etat,"0000","11111111");
 * 
 * mockMvc.perform(post("/api/users") .contentType(MediaType.APPLICATION_JSON)
 * .content(objectMapper.writeValueAsString(newUser)))
 * .andExpect(status().isOk())
 * .andExpect(jsonPath("$.username").value("Ahmed")); // Vérifier en base
 * assertThat(userRepository.count()).isEqualTo(1); // 2. READ
 * mockMvc.perform(get("/api/users/1")) .andExpect(status().isOk())
 * .andExpect(jsonPath("$.email").value("ahmed@email.com")); // 3. UPDATE
 * AgentGa updated = new AgentGa(1, "Ahmed",
 * "ahmed@email.com",true,etat,"0000","11111111");
 * 
 * mockMvc.perform(put("/api/users/1") .contentType(MediaType.APPLICATION_JSON)
 * .content(objectMapper.writeValueAsString(updated)))
 * .andExpect(status().isOk())
 * .andExpect(jsonPath("$.username").value("Ahmed Updated")); // 4. DELETE
 * mockMvc.perform(delete("/api/users/1")) .andExpect(status().isNoContent());
 * assertThat(userRepository.count()).isEqualTo(0); }
 * 
 * @Test
 * 
 * @Order(2)
 * 
 * @DisplayName("Test de performance: création de 100 utilisateurs") void
 * performanceTest() { long start = System.currentTimeMillis(); EtatAgentGa etat
 * = new EtatAgentGa(); etat.setIdEtatAgentGa(1);
 * etat.setLibEtatAgentGa("active"); for (int i = 0; i < 100; i++) {
 * userRepository.save(new AgentGa(i, "User" + i
 * ,"@test.com"+i,true,etat,"0000"+i,"11111111")); } long duration =
 * System.currentTimeMillis() - start;
 * assertThat(userRepository.count()).isEqualTo(100);
 * assertThat(duration).isLessThan(10000); // < 10 secondes }
 * 
 * }
 */