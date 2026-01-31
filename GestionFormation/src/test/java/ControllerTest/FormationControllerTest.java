package ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.GestionFormationApplication;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.controller.FormationController;
import iteam.tn.gestionformation.controller.ParticipantContoller;
import iteam.tn.gestionformation.service.FormationService;
import iteam.tn.gestionformation.service.TypeFormationService;
import org.junit.jupiter.api.BeforeEach;
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
import iteam.tn.gestionformation.model.Formation ;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import iteam.tn.gestionformation.model.TypeFormation ;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ContextConfiguration(classes = GestionFormationApplication.class)
@ExtendWith(MockitoExtension.class)

@DisplayName("Tests unitaires du Controller formation")
@WebMvcTest(FormationController.class)
class FormationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TypeFormationService typeFormationService;

    @MockBean
    private FormationService formationService;

    @MockBean
    private AuthClient authClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Formation formation;
    private FormationCreateDto formationDto;

    @BeforeEach
    void setUp() {
        formation = new Formation(); // Supposez que vous avez un constructeur ou des setters
        formation.setId(1L);

        formationDto = new FormationCreateDto();
        // Remplissez votre DTO selon vos champs
    }

    @Test
    void creerFormation_ShouldReturnSuccess() throws Exception {
        // Simulation de la réponse AuthClient
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("idAgent", 123);

        Mockito.when(authClient.getUserIdConnecte(anyString())).thenReturn(userMap);
        Mockito.when(formationService.creerFormation(any(FormationCreateDto.class), eq(123)))
                .thenReturn(formation);

        mockMvc.perform(post("/api/formations") // Ajustez le path si nécessaire
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formationDto)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.response.statut").value(null))
                .andExpect(jsonPath("$.response.id").value(1));
    }

    @Test
    void getAllTypeFormation_ShouldReturnList() throws Exception {
        TypeFormation type = new TypeFormation();
        Mockito.when(typeFormationService.listerTypes()).thenReturn(Collections.singletonList(type));

        mockMvc.perform(get("/api/formations/allTypeFormation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getById_ShouldReturnFormation() throws Exception {
        Mockito.when(formationService.getById(1L)).thenReturn(formation);

        mockMvc.perform(get("/api/formations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_ShouldReturnApiResponse() throws Exception {
        Mockito.when(formationService.update(eq(1L), any(FormationCreateDto.class)))
                .thenReturn(formation);

        mockMvc.perform(put("/api/formations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formationDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(1));
    }
}