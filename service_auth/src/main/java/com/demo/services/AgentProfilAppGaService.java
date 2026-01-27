package com.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.model.AgentGa;
import com.demo.model.AgentProfilAppGa;
import com.demo.repository.AgentProfilAppGaRepository;
import com.demo.tools.GlobalExceptionHandler;


@Service
public class AgentProfilAppGaService {
	@Value("${spring.idApplicationGa}")
	private Integer idActuelleApplicationGa;

	@Autowired
	private AgentProfilAppGaRepository agentProfilAppGaRepository;

	public AgentProfilAppGa findAgentProfilAppGaByAgentAndApp(AgentGa agentGa) {
		try {
			return agentProfilAppGaRepository.findByEtatAgentProfAppGaAndAgentGaAndApplicationGa_idAppGa(1, agentGa,
					idActuelleApplicationGa);
		} catch (Exception e) {

			throw new GlobalExceptionHandler("Impossible de récupérer  agent par l'email ", e);
		}
	}

}
