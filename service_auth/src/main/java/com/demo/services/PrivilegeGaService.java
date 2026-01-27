package com.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.PrivilegeGa;
import com.demo.model.ProfilGa;
import com.demo.model.ProfilPrivilegeGa;
import com.demo.repository.ProfilPrivilegeGaRepository;
import com.demo.tools.GlobalExceptionHandler;



@Service
public class PrivilegeGaService {

	@Autowired
	private ProfilPrivilegeGaRepository profilPrivilegePrjRepository;

	public List<PrivilegeGa> findByProfilGa(ProfilGa profilGa) {
		try {

			return profilPrivilegePrjRepository.findByProfilGaAndEtatPrivProfilGa(profilGa, 1).stream()
					.map(ProfilPrivilegeGa::getPrivilegeGa).collect(Collectors.toList());
		} catch (Exception e) {
			throw new GlobalExceptionHandler("Impossible de récupérer les privilèges pour le profil donné", e);
		}
	}

}
