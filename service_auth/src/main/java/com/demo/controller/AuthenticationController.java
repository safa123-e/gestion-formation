package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.DetailUserDTO;
import com.demo.dto.UpdatePasswordRequest;
import com.demo.dto.UtilisateurRegisterDTO;
import com.demo.dto.UtilisateursDTO;
import com.demo.model.AgentGa;
import com.demo.services.AgentGaService;
import com.demo.services.AuthenticationService;
import com.demo.services.JwtService;
import com.demo.tools.SftpTools;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

	@Autowired
	JwtService jwtService;
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	private SftpTools sftpTools;
	@Autowired
	private AgentGaService agentGaService;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody UtilisateurRegisterDTO utilisateurRegisterDTO) {
		try {
			AgentGa registeredUser = authenticationService.signup(utilisateurRegisterDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
		} catch (RuntimeException e) {
			// CORRECTION : On met le message dans une Map pour avoir du JSON {"error": "..."}
			Map<String, String> errorBody = new HashMap<>();
			errorBody.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(errorBody);
		}
	}
	@Operation(summary = "User sign-in", description = "Authenticates a user by validating their credentials and returns an authentication token.")
	@SecurityRequirement(name = "apiKeyAuth")
	@PostMapping("/signin")
	public Map<String, Object> authenticate(@RequestBody UtilisateursDTO loginUserDto) {
		Map<String, Object> responseMap = new HashMap<>();

		try {

			// Authentification via le service
			Map<String, Object> authResult = authenticationService.authenticate(loginUserDto);

			// Vérification des codes de retour
			if (authResult.get("code").equals(0) || authResult.get("code").equals(-1)) {
				return authResult;
			}

			// Extraction de l'utilisateur
			UserDetails user = (UserDetails) authResult.get("user");

			// Génération du JWT token
			String jwtToken = jwtService.generateToken(user);

			// Ajout des détails de l'utilisateur dans la réponse
			DetailUserDTO userDetail = (DetailUserDTO) authResult.get("response");

			userDetail.setToken(jwtToken);

			// Mise à jour de la réponse
			authResult.put("response", userDetail);
			authResult.remove("user");
			return authResult;

		} catch (ClassCastException e) {
			responseMap.put("code", -1);
			responseMap.put("response", "Data type mismatch: " + e.getMessage());
			return responseMap;

		} catch (Exception e) {
			responseMap.put("code", -1);
			responseMap.put("response", "An unexpected error occurred: " + e.getMessage());
			return responseMap;
		}
	}

	@Operation(summary = "User logout", description = "Logs out the current user by invalidating their authentication session or token.")
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7); // Récupérer le token sans le préfixe "Bearer "
			authenticationService.blacklistToken(token); // Ajouter le token à la blacklist
			return ResponseEntity.ok("Déconnexion réussie. Le token a été invalidé.");
		}
		return ResponseEntity.badRequest().body("Aucun token valide fourni.");
	}

	@Operation(summary = "Update user password", description = "Allows a user to update their password by providing the current password and a new password.")

	@PostMapping("/updatePassword")
	public Map<String, Object> updatePassword(@RequestHeader("Authorization") String authHeader,
			@RequestBody UpdatePasswordRequest request) {
		Map<String, Object> result = new HashMap<>();

		try {

			String oldPasswordInput = request.getOldPassword();

			String newPasswordInput = request.getNewPassword();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			AgentGa userConnect = (AgentGa) auth.getPrincipal();

			UtilisateurRegisterDTO dto = new UtilisateurRegisterDTO();
			dto.setEmail(userConnect.getCinAgentGa());
			dto.setPassword(oldPasswordInput);

			Map<String, Object> authResult = authenticationService.updatePassword(dto, newPasswordInput);
			if (authResult.get("code").equals(0)) {
				result.put("code", -2);
				result.put("response", "user not exist");
				return result;
			}
			if (authResult.get("code").equals(-1)) {
				result.put("code", 0);
				result.put("response", "old password incorrect");
				return result;
			}
			if (authResult.get("code").equals(1)) {

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7); // Récupérer le token sans le préfixe "Bearer "
					authenticationService.blacklistToken(token); // Ajouter le token à la blacklist

				}

				result.put("code", 1);
				result.put("response", "mdp modif");
				return result;
			}

			return result;

		} catch (Exception e) {
			result.put("code", -1);
			result.put("response", "An unexpected error occurred: " + e.getMessage());
			return result;
		}
	}

}
