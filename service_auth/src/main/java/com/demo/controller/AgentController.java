package com.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.model.AgentGa;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import com.demo.dto.AgentGaDto;
import com.demo.services.AgentGaService;
@SecurityRequirement(name = "bearerAuth")
@RestController
	@RequestMapping("/agents")
	public class AgentController {
@Autowired
	    private  AgentGaService userService;

	@GetMapping("/idConnecte")
	public Map<String, Object> getUserConnecteId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		AgentGa userConnect = (AgentGa) auth.getPrincipal();
		System.out.println("kkkkkkk"+userConnect.getCinAgentGa());

		Integer idAgent = userConnect.getIdAgentGa();
		Integer idService = userConnect.getServiceGa().getIdServiceGt();
		Boolean isResponsable = userConnect.getIsResponsableGa(); // boolean

		Map<String, Object> result = new HashMap<>();
		result.put("idAgent", idAgent);
		result.put("idService", idService);
		result.put("isResponsable", isResponsable);

		return result;
	}



}



