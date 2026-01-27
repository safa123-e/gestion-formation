package com.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.PrivilageDTO;
import com.demo.model.AgentGa;
import com.demo.model.AgentPrivGa;
import com.demo.model.PrivilegeGa;
import com.demo.model.ProfilGa;
import com.demo.model.ProfilPrivilegeGa;
import com.demo.repository.AgentPrivGaRepository;
import com.demo.repository.ProfilPrivilegeGaRepository;



@Service
public class AgentPrivGaService {

	@Autowired
	private AgentPrivGaRepository agentPrivGaRepository;
	@Autowired
	private ProfilPrivilegeGaRepository profilPrivilegeGaRepository;

	public List<PrivilageDTO> getPrivilegesForUserByProfil(AgentGa utilisateur, ProfilGa profil, Integer etat) {
		if (utilisateur == null) {
			throw new IllegalArgumentException("Utilisateur cannot be null");
		}
		List<AgentPrivGa> listAllPrivUser = agentPrivGaRepository.findByAgentGaAndEtatAgentPrivGa(utilisateur, etat);

		List<PrivilegeGa> listPrivByProfil = profilPrivilegeGaRepository.findByProfilGaAndEtatPrivProfilGa(profil, 1)
				.stream().map(ProfilPrivilegeGa::getPrivilegeGa).collect(Collectors.toList());

		List<PrivilageDTO> listPrivilege = new ArrayList<>();
		for (AgentPrivGa agentPriv : listAllPrivUser) {
			if (listPrivByProfil.contains(agentPriv.getPrivilegeGa())) {
				PrivilageDTO p = new PrivilageDTO();
				p.setIdPriv(agentPriv.getPrivilegeGa().getIdPrivilegeGa());
				p.setLibPriv(agentPriv.getPrivilegeGa().getLibPrivilegeArGa());
				listPrivilege.add(p);
			}

		}
		return listPrivilege;
	}

}
