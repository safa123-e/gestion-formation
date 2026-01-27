package com.demo.controller;


import java.util.List;

import com.demo.model.AgentGa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.AgentGaDto;
import com.demo.services.AgentGaService;

@RestController
	@RequestMapping("/agents")
	public class AgentController {
@Autowired
	    private  AgentGaService userService;

	@PostMapping("/idConnecte")
	public Integer getUserConnecteId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AgentGa userConnect = (AgentGa) auth.getPrincipal();
		return userConnect.getIdAgentGa();
	}

}



