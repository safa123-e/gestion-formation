package controllertest;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.demo.controller.AuthenticationController;
import com.demo.dto.DetailUserDTO;
import com.demo.dto.UpdatePasswordRequest;
import com.demo.dto.UtilisateurRegisterDTO;
import com.demo.dto.UtilisateursDTO;
import com.demo.model.AgentGa;
import com.demo.services.AgentGaService;
import com.demo.services.AuthenticationService;
import com.demo.services.JwtService;
import com.demo.tools.SftpTools;
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
    @DisplayName("Signup - Succès")
    void signup_Success() throws Exception {
        // 1. Préparation
        UtilisateurRegisterDTO dto = new UtilisateurRegisterDTO();
        dto.setEmail("ahmed@test.com");
        dto.setPassword("1234");

        AgentGa agent = new AgentGa();
        agent.setNomPrenomAgentGa("Ahmed");

        // On mocke le service pour renvoyer l'OBJET
        when(authenticationService.signup(any(UtilisateurRegisterDTO.class))).thenReturn(agent);

        // 2. Action et Vérification
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                // CORRECTION ICI :
                .andExpect(status().isCreated()) // On attend 201 et non plus 200
                .andExpect(jsonPath("$.nomPrenomAgentGa").value("Ahmed"));
       }
    @Test
    void signup_Failure_Conflict() throws Exception {
        UtilisateurRegisterDTO dto = new UtilisateurRegisterDTO();
        dto.setEmail("existing@test.com");

        when(authenticationService.signup(any()))
                .thenThrow(new RuntimeException("Email déjà pris"));

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // Crucial !
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email déjà pris")); // Maintenant ça marchera !
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

        // On prépare la map de retour du service
        Map<String, Object> authResult = new HashMap<>();
        authResult.put("code", 1);
        authResult.put("response", detailUser); // Contient les infos user

        when(authenticationService.authenticate(any(UtilisateursDTO.class))).thenReturn(authResult);
        when(jwtService.generateToken(any())).thenReturn("jwt-token");

        mockMvc.perform(post("/auth/signin")
                        .header("x-api-key", "test-token")
                        .accept(MediaType.APPLICATION_JSON) // <--- AJOUTEZ CETTE LIGNE ICI
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                // Vérifiez si votre controller met le token dans 'response' ou à la racine
                .andExpect(jsonPath("$.response.token").value("jwt-token"));    }

   /* @Test
    @DisplayName("POST /auth/signin - échec (Identifiants invalides)")
    void signin_Failure() throws Exception {
        // GIVEN : Le service simule un échec (code 0 par exemple)
        Map<String, Object> failureResult = new HashMap<>();
        failureResult.put("code", 0);
        failureResult.put("message", "Login ou mot de passe incorrect");

        when(authenticationService.authenticate(any())).thenReturn(failureResult);

        // WHEN & THEN
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"wrong\", \"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized()) // Ou .isOk() selon ta logique
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Login ou mot de passe incorrect"));
    }*/
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
        serviceResult.put("message", "mdp modif"); // Utilisons 'message' plutôt que 'response' si c'est du texte

        when(authenticationService.updatePassword(any(), any())).thenReturn(serviceResult);

        mockMvc.perform(post("/auth/updatePassword")
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON) // <--- AJOUTEZ CETTE LIGNE
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1));
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

}
