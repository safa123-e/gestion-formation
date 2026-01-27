package com.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.ServiceGa;
import com.demo.repository.ServiceGaRepository;


@Service
public class ServiceGaService {

	@Autowired
	private ServiceGaRepository serviceGaRepository;

	

	public ServiceGa findServiceGaById(Integer id) {
		return serviceGaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ServiceGa introuvable avec cet identifiant: " + id));
	}
}
