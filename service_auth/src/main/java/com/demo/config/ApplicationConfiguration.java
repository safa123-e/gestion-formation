package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.demo.model.AgentGa;
import com.demo.repository.AgentGaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ApplicationConfiguration {

	private final AgentGaRepository agentGtRepository;

	public ApplicationConfiguration(AgentGaRepository agentGtRepository) {

		this.agentGtRepository = agentGtRepository;
	}

	@Bean
	UserDetailsService userDetailsService() {

		return username -> {
			AgentGa userEntity = agentGtRepository.findFirstByCinAgentGaAndEtatAgentGa_idEtatAgentGa(username, 1);
			if (userEntity == null) {
				throw new UsernameNotFoundException("User not found");
			}
			return (UserDetails) userEntity;
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}



	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
															PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}


}