package com.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.model.AgentGa;
import com.demo.model.ServiceGa;
import com.demo.services.ServiceGaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import com.demo.dto.AgentGaDto;
import com.demo.services.AgentGaService;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor

	@RequestMapping("/agents")
	public class AgentController {

	private final AgentGaService userService ;
	private final ServiceGaService serviceGaService ;

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

	@GetMapping("Allservice")
	public List<ServiceGa> findAll() {
		return serviceGaService.findAll();
	}

}



