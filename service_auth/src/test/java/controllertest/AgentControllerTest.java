package controllertest;
import static org.hamcrest.Matchers.hasSize;
import com.demo.controller.AgentController;
import com.demo.controller.AuthenticationController;
import com.demo.dto.AgentGaDto;
import com.demo.model.AgentGa;
import com.demo.model.ServiceGa;
import com.demo.services.AgentGaService;
import com.demo.services.ServiceGaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de AgentController - Récupération par IDs")
class AgentControllerTest {

    private  MockMvc mockMvc;

    @MockBean
    private AgentGaService userService;

    @MockBean
    private ServiceGaService serviceGaService;
    @MockBean
    private AgentGa mockAgent;
    @MockBean
    private ServiceGa mockService;
    @InjectMocks
    private AgentController agentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(agentController).build();

        // Préparation du Service
        mockService = new ServiceGa();
        mockService.setIdServiceGt(101);
        mockService.setLibServiceGa("Service Informatique");

        // Préparation de l'Agent
        mockAgent = new AgentGa();
        mockAgent.setIdAgentGa(50);
        mockAgent.setCinAgentGa("AB12345");
        mockAgent.setIsResponsableGa(true);
        mockAgent.setServiceGa(mockService);
    }

    @Test
    void getUserConnecteId_ShouldReturnMapFromSecurityContext() throws Exception {
        // 1. On s'assure que le SecurityContext est propre
        SecurityContextHolder.clearContext();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(mockAgent, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/agents/idConnecte")
                        .accept(MediaType.APPLICATION_JSON)) // On force l'acceptation du JSON
                .andDo(print()) // <--- AJOUTEZ CECI pour voir ce que le test reçoit réellement
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idAgent").value(50));
    }

}