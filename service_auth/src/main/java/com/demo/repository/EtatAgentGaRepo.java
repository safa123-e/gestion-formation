package com.demo.repository;

import com.demo.model.EtatAgentGa;
import com.demo.model.ProfilGa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

    public interface EtatAgentGaRepo extends JpaRepository<EtatAgentGa, String>, JpaSpecificationExecutor<ProfilGa> {

    }
