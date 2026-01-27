package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.model.ProfilGa;



public interface ProfilGaRepository extends JpaRepository<ProfilGa, String>, JpaSpecificationExecutor<ProfilGa> {

}
