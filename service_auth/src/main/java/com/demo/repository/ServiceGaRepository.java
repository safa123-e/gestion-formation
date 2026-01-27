package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.model.ServiceGa;


public interface ServiceGaRepository extends JpaRepository<ServiceGa, Integer>, JpaSpecificationExecutor<ServiceGa> {
}
