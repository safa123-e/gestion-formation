package com.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.dto.AgentGaDto;
import com.demo.model.AgentGa;
import com.demo.repository.AgentGaRepository;
import com.demo.tools.GlobalExceptionHandler;



@Service
public class AgentGaService {

	private static final Logger logger = LoggerFactory.getLogger(AgentGaService.class);
	@Autowired
	private AgentGaRepository agentGaRepository;

	public AgentGa findAgentGaById(Integer id) {
		return agentGaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agent introuvable avec cet identifiant: " + id));
	}

	public AgentGa save(AgentGa agent) {
		try {
			return agentGaRepository.save(agent);

		} catch (Exception e) {
			logger.error("Erreur lors de l'enregistrement de l'agent : {}", agent, e);
			throw  new GlobalExceptionHandler("Impossible d'enregistrer l'agent pour le moment.", e);
		}
	}

	public AgentGa updateProfilAgent(AgentGa agent) {
		try {

			return agentGaRepository.save(agent);

		} catch (Exception e) {
			logger.error("Erreur lors de la mise à jour du profil de l'agent", e);
			throw new GlobalExceptionHandler("Erreur interne lors de la mise à jour du profil", e);
		}
	}

	public List<AgentGa> getAllAgentIsieActif(Boolean isIsie, Integer etatAgent) {
		try {

			Specification<AgentGa> spec = (root, query, cb) -> cb.and(
					cb.equal(root.get("etatAgentGa").get("idEtatAgentGa"), etatAgent),
					cb.equal(root.get("isIsieGa"), isIsie),
					cb.or(cb.isNull(root.get("matAgentGa")), cb.not(root.get("matAgentGa").in("0000")))

			);
			return agentGaRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "nomPrenomAgentGa"));

		} catch (Exception e) {

			throw new GlobalExceptionHandler("Impossible de récupérer les agents ", e);
		}

	}

	public AgentGa findAgentGaByEmailAgentGa(String emailAgentGa) {
		try {
			return agentGaRepository.findByEmailAgentGa(emailAgentGa);

		} catch (Exception e) {

			throw new GlobalExceptionHandler("Impossible de récupérer  agent par l'email ", e);
		}
	}

	public List<AgentGaDto> findByIds(Iterable<Integer> ids) {

	    List<AgentGa> agents = agentGaRepository.findAllById(ids);

	    List<AgentGaDto> listDto = new ArrayList<>();

	    for (AgentGa agent : agents) {
	        AgentGaDto dto = new AgentGaDto();
	        dto.setNomPrenomAgentGa(agent.getNomPrenomAgentGa());

	        dto.setTelAgentGa(agent.getTelAgentGa());

	        listDto.add(dto);
	    }

	    return listDto;
	}



}
