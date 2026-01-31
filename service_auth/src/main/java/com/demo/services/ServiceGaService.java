package com.demo.services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.ServiceGa;
import com.demo.repository.ServiceGaRepository;

@RequiredArgsConstructor

@Service
public class ServiceGaService {

	private final ServiceGaRepository serviceGaRepository;


	public List<ServiceGa> findAll() {
		return serviceGaRepository.findAll() ;

	}
	public ServiceGa findServiceGaById(Integer id) {
		return serviceGaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ServiceGa introuvable avec cet identifiant: " + id));
	}
}
