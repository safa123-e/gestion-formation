
package com.demo.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.config.ApiKeyAuthentication;
import com.demo.dto.DetailUserDTO;
import com.demo.dto.UtilisateurRegisterDTO;
import com.demo.dto.UtilisateursDTO;
import com.demo.model.AgentGa;
import com.demo.model.AgentProfilAppGa;
import com.demo.repository.AgentGaRepository;
import com.demo.repository.AgentProfilAppGaRepository;
import com.demo.tools.SftpTools;
import com.jcraft.jsch.ChannelSftp;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class AuthenticationService {

	private final Set<String> blacklistedTokens = new HashSet<>();
	@Autowired
	private AgentGaRepository agentGtRepository;

	@Autowired
	private SftpTools sftpTools;

	private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
	private static final String AUTH_TOKEN = "Baeldung";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AgentPrivGaService agentPrivGtService;

	@Autowired
	private AgentProfilAppGaRepository agentProfilAppGaRepository;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	@Value("${spring.idApplicationGa}")

	private Integer idActuelleApplicationGa;

	public AgentGa signup(UtilisateurRegisterDTO input) {

		var user = new AgentGa();
		user.setCinAgentGa(input.getEmail());
		user.setPwdAgentGa(passwordEncoder.encode(input.getPassword()));
		return agentGtRepository.save(user);
	}

	@Transactional
	public Map<String, Object> authenticate(UtilisateursDTO input) {
		try {
			ChannelSftp sftpChannel = null;
			Map<String, Object> response = new HashMap<>();
			AgentProfilAppGa agentProfilAppGa = agentProfilAppGaRepository
					.findByEtatAgentProfAppGaAndAgentGa_cinAgentGaAndApplicationGa_idAppGa(1, input.getEmail(),
							idActuelleApplicationGa);
			if (agentProfilAppGa == null) {
				response.put("code", -1);
				response.put("response", "User is not authorized for this application or user not found ");

				return response;
			} else {

				AgentGa user = agentProfilAppGa.getAgentGa();
				DetailUserDTO userDetail = new DetailUserDTO();
				userDetail.setApplication(agentProfilAppGa.getApplicationGa().getLibAppGa());
				userDetail.setLogin(user.getCinAgentGa());
				userDetail.setPremierAcces(user.getFirstAccessAgentGa());
				userDetail.setNomPrenomAgentGa(user.getNomPrenomAgentGa());
				if (user.getServiceGa() != null) {
					userDetail.setLibLieuTravail(user.getServiceGa().getLibServiceGa());
				} else {
					userDetail.setLibLieuTravail("الادارة المركزية");
				}
				userDetail.setLibProfil(agentProfilAppGa.getProfilGa().getLibProfilGaAr());
				userDetail.setUtilisPriv(
						agentPrivGtService.getPrivilegesForUserByProfil(user, agentProfilAppGa.getProfilGa(), 1));
				userDetail.setCinAgentGa(user.getCinAgentGa());
				userDetail.setEmailAgentGa(user.getEmailAgentGa());
				userDetail.setMatAgentGa(user.getMatAgentGa());
				userDetail.setTelAgentGa(user.getTelAgentGa());
				//sftpChannel = sftpTools.connectSftp();

				if (user.getImageUrl() != null) {
					byte[] imageBytes = null;

					//imageBytes = sftpTools.getImageFromFtp(user.getImageUrl(), sftpChannel);

					userDetail.setImageBytes(imageBytes);
				}
				authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

				response.put("code", 1);
				response.put("response", userDetail);
				response.put("user", user);

			//	sftpTools.disconnectSftp(sftpChannel);

				return response;

			}

		} catch (Exception e) {
			logger.error("Exception******** {}", e.getMessage());
			return null;
		}
	}

	public void blacklistToken(String token) {
		blacklistedTokens.add(token);
	}

	@Transactional
	public Map<String, Object> updatePassword(UtilisateurRegisterDTO input, String newPassword) {
		Map<String, Object> response = new HashMap<>();
		try {

			AgentGa user = agentGtRepository.findFirstByCinAgentGaAndEtatAgentGa_idEtatAgentGa(input.getEmail(), 1);

			if (user == null) {
				response.put("code", 0);
				response.put("response", "User not found");
				return response;
			}

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

			user.setPwdAgentGa((passwordEncoder.encode(newPassword)));
			user.setFirstAccessAgentGa(true);
			agentGtRepository.save(user);

			response.put("code", 1);
			response.put("response", "correct password and modifier");

			return response;

		} catch (Exception e) {
			response.put("code", -1);
			response.put("response", "An unexpected error occurred: " + e.getMessage());
			return response;
		}

	}

	public boolean isTokenBlacklisted(String token) {
		return blacklistedTokens.contains(token);
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
			throw new BadCredentialsException("Invalid API Key");
		}

		return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}