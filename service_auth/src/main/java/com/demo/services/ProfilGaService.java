package com.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.demo.model.ProfilGa;
import com.demo.repository.ProfilGaRepository;
import com.demo.tools.GlobalExceptionHandler;


@Service
public class ProfilGaService {
	@Autowired
	private ProfilGaRepository profilGaRepository;

	public ProfilGa findProfilById(String idProfil) {
		try {

			Optional<ProfilGa> profilOptional = profilGaRepository.findById(idProfil);
			if (profilOptional.isPresent()) {
				return profilOptional.get();
			} else {
				throw new NotFoundException();
			}
		} catch (Exception e) {
			throw new GlobalExceptionHandler("Impossible de récupérer le profil pour le moment.", e);
		}
	}

}
