package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.model.AgentGa;
import com.demo.model.AgentPrivGa;
import com.demo.model.AgentPrivGaPK;



public interface AgentPrivGaRepository
		extends JpaRepository<AgentPrivGa, AgentPrivGaPK>, JpaSpecificationExecutor<AgentPrivGa> {

	List<AgentPrivGa> findByAgentGaAndEtatAgentPrivGa(AgentGa agentGa, Integer etatAgentPrivGa);

}
