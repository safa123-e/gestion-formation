package controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tn.isie.auth.controller.AuthenticationController;
import tn.isie.auth.dto.*;
import tn.isie.auth.model.AgentGa;
import tn.isie.auth.services.*;
import tn.isie.auth.tools.SftpTools;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires de controller authentification")

class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private SftpTools sftpTools;

    @Mock
    private AgentGaService agentGaService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        objectMapper = new ObjectMapper();
    }

    // ======================= SIGNUP =======================

    @Test
    @DisplayName("POST /auth/signup - succès")
    void signup_Success() throws Exception {
        UtilisateurRegisterDTO dto = new UtilisateurRegisterDTO();
        dto.setemail("11111111");
        dto.setPassword("1234");

        AgentGa agent = new AgentGa();
        agent.setNomPrenomAgentGa("Ahmed");

        when(authenticationService.signup(any(UtilisateurRegisterDTO.class)))
                .thenReturn(agent);

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nomPrenomAgentGa").value("Ahmed"));
    }

    // ======================= SIGNIN =======================

    @Test
    @DisplayName("POST /auth/signin - succès")
    void signin_Success() throws Exception {
        UtilisateursDTO loginDto = new UtilisateursDTO();
        loginDto.setEmail("11111111");
        loginDto.setPassword("1234");

        DetailUserDTO detailUser = new DetailUserDTO();
        detailUser.setNomPrenomAgentGa("Ahmed");

        Map<String, Object> authResult = new HashMap<>();
        authResult.put("code", 1);
        authResult.put("user", mock(org.springframework.security.core.userdetails.UserDetails.class));
        authResult.put("response", detailUser);

        when(authenticationService.authenticate(any(UtilisateursDTO.class)))
                .thenReturn(authResult);
        when(jwtService.generateToken(any()))
                .thenReturn("jwt-token");

        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(1))
            .andExpect(jsonPath("$.response.token").value("jwt-token"));
    }

    // ======================= LOGOUT =======================

    @Test
    @DisplayName("POST /auth/logout - succès")
    void logout_Success() throws Exception {
        doNothing().when(authenticationService).blacklistToken(any());

        mockMvc.perform(post("/auth/logout")
                .header("Authorization", "Bearer test-token"))
            .andExpect(status().isOk())
            .andExpect(content().string("Déconnexion réussie. Le token a été invalidé."));
    }

    // ======================= UPDATE PASSWORD =======================

    @Test
    @DisplayName("POST /auth/updatePassword - succès")
    void updatePassword_Success() throws Exception {
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setOldPassword("oldPass");
        request.setNewPassword("newPass");

        AgentGa agent = new AgentGa();
        agent.setCinAgentGa("12345678");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(agent, null)
        );

        Map<String, Object> serviceResult = new HashMap<>();
        serviceResult.put("code", 1);

        when(authenticationService.updatePassword(any(), any()))
                .thenReturn(serviceResult);

        mockMvc.perform(post("/auth/updatePassword")
                .header("Authorization", "Bearer test-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(1))
            .andExpect(jsonPath("$.response").value("mdp modif"));
    }
}
