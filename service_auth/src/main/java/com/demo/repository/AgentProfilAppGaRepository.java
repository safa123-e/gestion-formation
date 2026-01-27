package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.model.AgentGa;
import com.demo.model.AgentProfilAppGa;
import com.demo.model.AgentProfilAppGaPK;


public interface AgentProfilAppGaRepository
		extends JpaRepository<AgentProfilAppGa, AgentProfilAppGaPK>, JpaSpecificationExecutor<AgentProfilAppGa> {

	AgentProfilAppGa findByEtatAgentProfAppGaAndAgentGa_cinAgentGaAndApplicationGa_idAppGa(Integer etat, String cin,
			Integer idApp);

	AgentProfilAppGa findByEtatAgentProfAppGaAndAgentGaAndApplicationGa_idAppGa(Integer etat, AgentGa agentGa,
			Integer idApp);

}
