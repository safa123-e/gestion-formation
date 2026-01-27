package integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import com.demo.FormationServiceApplication;
import com.demo.dto.UtilisateurRegisterDTO;
import com.demo.model.AgentGa;
import com.demo.model.EtatAgentGa;
import com.demo.repository.AgentGaRepository;
import com.demo.repository.AgentProfilAppGaRepository;
import com.demo.repository.EtatAgentGaRepo;
import com.demo.services.AuthenticationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest(classes = FormationServiceApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Pour respecter l'ordre @Order
@Transactional // Très important pour nettoyer la base entre les tests
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgentGaRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationService authenticationService;
private EtatAgentGa etat ;
@Autowired
private EtatAgentGaRepo etatAgentGaRepo ;
    @BeforeEach
    void setup() {
        etat = new EtatAgentGa();
        etat.setIdEtatAgentGa(1);
        etat.setLibEtatAgentGa("active");
        etatAgentGaRepo.save(etat);
    }

    @Test
    @Order(1)
    @DisplayName("Scénario : Inscription d'un nouvel agent")
    void signupTest() throws Exception {
        // 1. On nettoie pour être sûr du résultat (si @Transactional n'est pas utilisé)
        userRepository.deleteAll();

        // GIVEN
        UtilisateurRegisterDTO dto = new UtilisateurRegisterDTO();
        dto.setEmail("11111111");
        dto.setPassword("1234");

        // WHEN & THEN
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cinAgentGa").value("11111111"));

        // 2. Vérification robuste : on vérifie que le compte est bien de 1
        assertThat(userRepository.count()).isEqualTo(1L);

        // 3. Vérification de l'existence spécifique
        assertThat(userRepository.findFirstByCinAgentGaAndEtatAgentGa_idEtatAgentGa("11111111", null))
                .isNotNull();
    }
    @Test
    @Order(2)
    void signinTest() throws Exception {
        // GIVEN : On s'assure que l'utilisateur est bien en base AVANT de tenter le login
        // Si le signupTest a fait un rollback, on doit le recréer ici ou dans un @BeforeEach
        UtilisateurRegisterDTO regDto = new UtilisateurRegisterDTO();
        regDto.setEmail("11111111");
        regDto.setPassword("1234");

        // On force l'inscription si elle n'existe pas (pour l'isolation du test)
        if (userRepository.findFirstByCinAgentGaAndEtatAgentGa_idEtatAgentGa("11111111", null) == null) {
            authenticationService.signup(regDto);
        }

        // WHEN : On tente le login
        mockMvc.perform(post("/auth/signin")
                        .header("x-api-key", "123456ABCDEF")
                        .with(csrf()) // Adds the required CSRF token for the POST request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regDto)))
                .andDo(print())
                .andExpect(status().isOk()) ;
    }
    @Test
    @Order(3)
    @DisplayName("Scénario : Échec de connexion (Mauvais password)")
    void signinFailureTest() throws Exception {
        UtilisateurRegisterDTO dto = new UtilisateurRegisterDTO();
        dto.setEmail("11111111");
        dto.setPassword("WRONG_PASSWORD");

        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized()); // Doit retourner 401
    }
}