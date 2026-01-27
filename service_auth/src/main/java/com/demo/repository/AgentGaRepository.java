package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.demo.model.AgentGa;


@Repository
public interface AgentGaRepository extends JpaRepository<AgentGa, Integer>, JpaSpecificationExecutor<AgentGa> {

	AgentGa findFirstByCinAgentGaAndEtatAgentGa_idEtatAgentGa(String cinAgentGt, Integer idEtatAgentGt);

	AgentGa findByEmailAgentGa(String emailAgentGa);

	AgentGa findByEmailAgentGaIn(List<String> emails);
	
	

}
